package com.dsinpractice.spikes.ds;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Tree<T> {

    TreeNode<T> root;

    static class TreeNode<T> {

        T value;
        private int level;
        List<TreeNode> children;

        TreeNode(T value, TreeNode parent) {
            this.value = value;
            children = new ArrayList<TreeNode>();
            if (parent == null) {
                level = 0;
            } else {
                level = parent.level+1;
            }

        }

        TreeNode<T> add(T value) {
            TreeNode<T> child = new TreeNode<T>(value, this);
            children.add(child);
            return child;
        }

        void delete(T value) {
            children.remove(value);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(value.toString()).append(",");
            for (TreeNode child : children) {
                sb.append(child.toString()).append(",");
            }
            return sb.substring(0, sb.length()-1);
        }

        public void preOrder() {
            System.out.println(this.value);
            if (children.size()>0) {
                TreeNode treeNode = children.get(0);
                treeNode.preOrder();
            }
            for (int i = 1; i < children.size(); i++) {
                TreeNode treeNode = children.get(i);
                treeNode.preOrder();
            }
        }

        public void bfs() {
            ArrayDeque<TreeNode> treeNodes = new ArrayDeque<TreeNode>();
            treeNodes.addLast(this);
            int lastLevel = this.level;
            while (!treeNodes.isEmpty()) {
                TreeNode treeNode = treeNodes.removeFirst();
                if (lastLevel != treeNode.level) {
                    System.out.println();
                    lastLevel = treeNode.level;
                }
                System.out.print(treeNode.value.toString() + " ");
                treeNodes.addAll(treeNode.children);
            }
        }
    }

    public Tree(TreeNode root) {
        this.root = root;
    }

    @Override
    public String toString() {
        return root.toString();
    }

    public void preOrder() {
        root.preOrder();
    }

    public void bfs() {
        root.bfs();
    }

    public static void main(String[] args) {
        TreeNode<Integer> one = new TreeNode<Integer>(1, null);
        Tree<Integer> tree = new Tree<Integer>(one);
        TreeNode<Integer> two = one.add(2);
        TreeNode<Integer> three = one.add(3);
        two.add(4);
        two.add(5);
        three.add(6);
        three.add(7);
        System.out.println(tree);
        tree.bfs();
    }
}
