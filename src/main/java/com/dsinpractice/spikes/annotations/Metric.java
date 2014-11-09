package com.dsinpractice.spikes.annotations;

public class Metric {

    private int value;

    public void incr() {
        value++;
    }

    public int value() {
        return value;
    }
}
