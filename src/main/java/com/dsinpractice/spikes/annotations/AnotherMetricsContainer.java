package com.dsinpractice.spikes.annotations;

public class AnotherMetricsContainer extends AbstractMetricsContainer {

    public AnotherMetricsContainer() {
        super();
        registerAll();
    }

    @MetricMetadata(name="metric1")
    public void handleMetric1() {
        incrMetricByName("metric1");
    }

    @MetricMetadata(name="metric2")
    public void handleMetric2() {
        incrMetricByName("metric2");
    }
}
