package com.dsinpractice.spikes.algos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ZeroOneListSort {

    public static void main(String[] args) {
        ZeroOneListSort zeroOneListSort = new ZeroOneListSort();
        zeroOneListSort.runSort();
    }

    private void runSort() {
        int size = 10;
        List<Integer> bits = new ArrayList<Integer>(Collections.nCopies(size, 0));
        Collections.fill(bits.subList(size / 2, size), 1);
        System.out.println(bits);
        Collections.shuffle(bits);
//        List<Integer> bits = Arrays.asList(0, 0, 1, 0, 0, 1, 1, 1, 1, 0);
        System.out.println(bits);
        int i = 0;
        int j = bits.size()-1;
        while (i < j) {
            while (i < bits.size() && bits.get(i) == 0) i++;
            while (j >= 0 && bits.get(j) == 1) j--;
            if (i < j) Collections.swap(bits, i, j);
            System.out.println(bits);
            i++;
            j--;
        }
        System.out.println(bits);
    }
}
