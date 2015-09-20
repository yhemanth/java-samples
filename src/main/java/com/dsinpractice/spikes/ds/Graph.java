package com.dsinpractice.spikes.ds;

import java.util.*;

public class Graph {

    Map<String, Set<String>> adjacencyMatrix;

    public Graph() {
        adjacencyMatrix = new HashMap<String, Set<String>>();
    }

    public void addEdge(String v, String... ui) {
        Set<String> neighbours = adjacencyMatrix.get(v);
        if (neighbours == null) {
            neighbours = new LinkedHashSet<String>();
            adjacencyMatrix.put(v, neighbours);
        }
        for (String u : ui) {
            neighbours.add(u);
        }
    }

    public void dfs(String v, Visitor visitor) {
        accept(v, new HashSet<String>(), visitor);
    }

    public void dfsNonRecursive(String v, Visitor visitor) {
        acceptNonRecursive(v, new HashSet<String>(), visitor);
    }

    public void bfs(String v, Visitor visitor) {
        acceptBfs(v, new HashSet<String>(), visitor);

    }

    private void acceptBfs(String v, HashSet<String> visited, Visitor visitor) {
        Deque<String> queue = new ArrayDeque<String>();
        queue.addLast(v);
        String u = queue.poll();
        while (u != null) {
            if (!visited.contains(u)) {
                visited.add(u);
                visitor.visit(u);
                Set<String> neighbours = adjacencyMatrix.get(u);
                if (neighbours != null) {
                    for (String n : neighbours) {
                        queue.addLast(n);
                    }
                }
            }
            u = queue.poll();
        }
    }

    private void acceptNonRecursive(String v, HashSet<String> visited, Visitor visitor) {
        Deque<String> stack = new ArrayDeque<String>();
        stack.addFirst(v);
        String u = stack.poll();
        while (u != null) {
            if (!visited.contains(u)) {
                visited.add(u);
                visitor.visit(u);
                Set<String> neighbours = adjacencyMatrix.get(u);
                if (neighbours != null) {
                    for (String n : neighbours) {
                        stack.addFirst(n);
                    }
                }
            }
            u = stack.poll();
        }
    }

    private void accept(String v, Set<String> visited, Visitor visitor) {
        visited.add(v);
        visitor.visit(v);
        Set<String> neighbours = adjacencyMatrix.get(v);
        if (neighbours != null) {
            for (String n : neighbours) {
                if (!visited.contains(n))
                    accept(n, visited, visitor);
            }
        }
    }

    public void print() {
        System.out.println(adjacencyMatrix);
    }

    interface Visitor {
        void visit(String v);
    }

    static class PrintVisitor implements Visitor {
        @Override
        public void visit(String v) {
            System.out.println("Visited: " + v);
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addEdge("A", "B", "C", "E");
        graph.addEdge("B", "D", "F", "E");
        graph.addEdge("C", "G");
        graph.print();
        graph.dfs("A", new PrintVisitor());
        System.out.println("-----------");
        graph.dfsNonRecursive("A", new PrintVisitor());
        System.out.println("-----------");
        graph.bfs("A", new PrintVisitor());
    }
}
