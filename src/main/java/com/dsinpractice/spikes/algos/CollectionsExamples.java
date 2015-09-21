package com.dsinpractice.spikes.algos;

import java.util.*;

public class CollectionsExamples {

    public static void main(String[] args) {
        //randomizeArguments();
//        deduplicateArguments("This is a string with A set of Mixed Case String words.".split(" "));
        counter("one two three two three four four three four four".split(" "));
    }

    private static void counter(String[] args) {
        TreeMap<String, Integer> frequencyCounts = new TreeMap<String, Integer>();
        for (String arg : args) {
            int nTimes = Collections.frequency(Arrays.asList(args), arg);
            frequencyCounts.put(arg, nTimes);
        }
        System.out.println(frequencyCounts);
    }


    private static void deduplicateArguments(String[] args) {
        SortedSet<String> tokens = new TreeSet<String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });
        tokens.addAll(Arrays.asList(args));
        System.out.println(tokens);
    }

    private static void randomizeArguments() {
        String[] val = "This is a string which I want to tokenize and print in random order".split(" ");
        List<String> tokens = Arrays.asList(val);
        Collections.shuffle(tokens);
        System.out.println(tokens);
    }
}
