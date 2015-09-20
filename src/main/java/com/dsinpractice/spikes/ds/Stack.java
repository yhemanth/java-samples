package com.dsinpractice.spikes.ds;

import java.util.ArrayDeque;
import java.util.Deque;

public class Stack<E> {

    private Deque<E> deque = new ArrayDeque<E>();

    public void push(E elem) {
        deque.addFirst(elem);
    }

    public E pop() {
        return deque.remove();
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(3);
        stack.push(1);
        stack.push(4);
        stack.push(1);
        stack.push(5);
        stack.push(9);

        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }

    private boolean isEmpty() {
        return deque.isEmpty();
    }
}
