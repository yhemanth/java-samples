package com.dsinpractice.spikes.annotations;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AbstractMetricsContainer {
    protected final MetricsRegistry metricsRegistry;
    protected Map<String, Metric> metricsMap = new HashMap<String, Metric>();

    public AbstractMetricsContainer() {
        metricsRegistry = new MetricsRegistry();
    }

    protected void registerAll() {
        for (Method method : getClass().getDeclaredMethods()) {
            MetricMetadata metricMetaData = method.getAnnotation(MetricMetadata.class);
            if (metricMetaData != null) {
                Metric metric = new Metric();
                metricsRegistry.register(metric, metricMetaData.name());
                metricsMap.put(metricMetaData.name(), metric);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder metricsString = new StringBuilder();
        for (String name : metricsRegistry.metricNames()) {
            metricsString.append(name + ": " + metricsRegistry.get(name).value() + ", ");
        }

        return metricsString.substring(0, metricsString.length()-2);
    }

    protected void incrMetricByName(String name) {
        metricsMap.get(name).incr();
    }
}
