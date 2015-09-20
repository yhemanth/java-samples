package com.dsinpractice.spikes.annotations;

public class SampleMetricsContainer extends AbstractMetricsContainer {

    public SampleMetricsContainer() {
        super();
        registerAll();
    }

    @MetricMetadata(name= "some-metric")
    public void handleSomeMetricEvent() {
        incrMetricByName("some-metric");
    }

    @MetricMetadata(name= "some-other-metric")
    public void handleSomeOtherMetricEvent() {
        incrMetricByName("some-other-metric");
    }

    @MetricMetadata(name= "a-third-metric")
    public void handleAThirdMetricEvent() {
        incrMetricByName("a-third-metric");
    }

    @MetricMetadata(name= "a-fourth-metric")
    public void handleAFourthMetricEvent() {
        incrMetricByName("a-fourth-metric");
    }

}
