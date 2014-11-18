package com.dsinpractice.spikes.annotations;

public class MetricsRunner {

    public static void main(String[] args) {
        SampleMetricsContainer sampleMetrics = new SampleMetricsContainer();
        sampleMetrics.handleSomeMetricEvent();
        sampleMetrics.handleSomeMetricEvent();
        sampleMetrics.handleSomeOtherMetricEvent();
        sampleMetrics.handleAThirdMetricEvent();
        sampleMetrics.handleAFourthMetricEvent();
        sampleMetrics.handleAFourthMetricEvent();
        System.out.println(sampleMetrics);

        System.out.println("============================");

        AnotherMetricsContainer anotherMetricsContainer = new AnotherMetricsContainer();
        anotherMetricsContainer.handleMetric1();
        anotherMetricsContainer.handleMetric2();
        anotherMetricsContainer.handleMetric2();
        System.out.println(anotherMetricsContainer);
    }
}
