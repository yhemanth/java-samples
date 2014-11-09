package com.dsinpractice.spikes.annotations;

public class MetricsRunner {

    public static void main(String[] args) {
        SampleMetrics sampleMetrics = new SampleMetrics();
        sampleMetrics.handleSomeMetricEvent();
        sampleMetrics.handleSomeOtherMetricEvent();
        sampleMetrics.handleAThirdMetricEvent();
        sampleMetrics.handleAFourthMetricEvent();
        System.out.println(sampleMetrics);
    }
}
