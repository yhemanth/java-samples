package com.thoughtworks.spikes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Mimi {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String s = bufferedReader.readLine();
        while (s != null) {
            System.out.println("Hello, " + s + "!");
            s = bufferedReader.readLine();
        }
    }
}
