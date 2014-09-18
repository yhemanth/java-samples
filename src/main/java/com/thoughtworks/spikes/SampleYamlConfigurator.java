package com.thoughtworks.spikes;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class SampleYamlConfigurator {

    public static void main(String[] args) {
        SampleYamlConfigurator sampleYamlConfigurator = new SampleYamlConfigurator();
        sampleYamlConfigurator.load();
    }

    private void load() {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.yaml");
        Yaml yaml = new Yaml();
        Map<String, Object> configuration = (Map<String, Object>) yaml.load(resourceAsStream);
        handleKeyValuePairs(configuration);
    }

    private void handleKeyValuePairs(Map<String, Object> configuration) {
        for (String key : configuration.keySet()) {
            Object value = configuration.get(key);
            if (value instanceof Map) {
                handleKeyValuePairs((Map<String, Object>) value);
            } else if (value instanceof List) {
                List l = (List)value;
                for (Object obj: l) {
                    if (obj instanceof Map) {
                        handleKeyValuePairs((Map<String, Object>) obj);
                    }
                }
            } else {
                String configItem = String.format("%s -> %s [%s]", key, value, value.getClass().getName());
                System.out.println(configItem);
            }
        }
    }
}
