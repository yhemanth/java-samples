package com.dsinpractice.spikes.annotations;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MetricsRegistry {

    private Map<String, Metric> metricsMap = new HashMap<String, Metric>();

    public void register(Metric metric, String name) {
        metricsMap.put(name, metric);
    }

    public Iterable<String> metricNames() {
        return new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return metricsMap.keySet().iterator();
            }
        };
    }

    public Metric get(String name) {
        return metricsMap.get(name);
    }
}
