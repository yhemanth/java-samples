package com.dsinpractice.spikes.ds;

import java.util.*;

public class SetOperations {

    public static void main(String[] args) {
        String[] list1 = "you want to know which words".split(" ");
        String[] list2 = "you do not want any words".split(" ");
        List<String> l1 = Arrays.asList(list1);
        System.out.println(l1);
        List<String> l2 = Arrays.asList(list2);
        System.out.println(l2);
        Set<String> union = SetOperations.union(l1, l2);
        System.out.println(union);
        Set<String> intersection = SetOperations.intersection(l1, l2);
        System.out.println(intersection);
        Set<String> difference = SetOperations.difference(l1, l2);
        System.out.println(difference);
        Set<String> symmetricDifference = SetOperations.symmetricDifference(l1, l2);
        System.out.println(symmetricDifference);
    }

    private static Set<String> symmetricDifference(List<String> l1, List<String> l2) {
        Set<String> union = union(l1, l2);
        Set<String> intersection = intersection(l1, l2);
        union.removeAll(intersection);
        return union;
    }

    private static Set<String> difference(List<String> l1, List<String> l2) {
        Set<String> s1 = new HashSet<String>(l1);
        Set<String> s2 = new HashSet<String>(l2);
        s1.removeAll(s2);
        return s1;
    }

    private static Set<String> intersection(List<String> l1, List<String> l2) {
        Set<String> s1 = new HashSet<String>(l1);
        Set<String> s2 = new HashSet<String>(l2);
        s1.retainAll(s2);
        return s1;
    }

    private static Set<String> union(Collection<String> l1, Collection<String> l2) {
        Set<String> s1 = new HashSet<String>(l1);
        Set<String> s2 = new HashSet<String>(l2);
        s1.addAll(s2);
        return s1;
    }
}
