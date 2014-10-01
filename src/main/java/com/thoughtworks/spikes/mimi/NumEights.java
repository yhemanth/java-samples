package com.thoughtworks.spikes.mimi;

public class NumEights {

    public static void main(String[] args) {
        int num = Integer.parseInt(args[0]);
        int counts = 0;
        for (int i=1; i<=num; i++) {
            counts += findEights(i);
        }
        System.out.println(counts);
    }

    private static int findEights(int i) {
//        System.out.println(i);
        int count = 0;
        int quotient = i;
        do {
            int rem = quotient % 10;
            if (rem == 8) {
                count++;
            }
            quotient = quotient / 10;
        } while (quotient != 0);
//        System.out.println(count);
        return count;
    }
}
