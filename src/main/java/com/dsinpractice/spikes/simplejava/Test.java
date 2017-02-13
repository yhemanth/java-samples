package com.dsinpractice.spikes.simplejava;

public class Test {

    public static void main(String[] args) {
        if (null instanceof String) {
            System.out.println("OK");
        }
        StringBuilder sb = new StringBuilder();
        String x = "null";
        StringBuilder hello = sb.append("hello").append(x);
        System.out.println(hello.toString());
    }
}
