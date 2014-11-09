package com.dsinpractice.spikes.annotations;

import java.lang.reflect.Field;

public class SampleMetrics {

    private final MetricsRegistry metricsRegistry;

    @MetricMetadata(name= "some-metric")
    private Metric someMetric = new Metric();

    @MetricMetadata(name= "some-other-metric")
    private Metric someOtherMetric = new Metric();

    @MetricMetadata(name= "a-third-metric")
    private Metric aThirdMetric = new Metric();

    @MetricMetadata(name= "a-fourth-metric")
    private Metric aFourthMetric = new Metric();

    public SampleMetrics() {
        metricsRegistry = new MetricsRegistry();
        registerAll();
    }

    private void registerAll() {
        for (Field field : getClass().getDeclaredFields()) {
            MetricMetadata metricMetaData = field.getAnnotation(MetricMetadata.class);
            if (metricMetaData != null) {
                try {
                    metricsRegistry.register((Metric) field.get(this), metricMetaData.name());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void handleSomeMetricEvent() {
        someMetric.incr();
    }

    public void handleSomeOtherMetricEvent() {
        someOtherMetric.incr();
    }

    public void handleAThirdMetricEvent() {
        aThirdMetric.incr();
    }

    public void handleAFourthMetricEvent() {
        aFourthMetric.incr();
    }

    @Override
    public String toString() {
        StringBuilder metricsString = new StringBuilder();
        for (String name : metricsRegistry.metricNames()) {
            metricsString.append(name + ": " + metricsRegistry.get(name).value() + ", ");
        }

        return metricsString.substring(0, metricsString.length()-2);
    }
}
