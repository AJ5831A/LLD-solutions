package com.example.metrics;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Global metrics registry — proper thread-safe, lazy-initialized Singleton.
 *
 * Uses the static holder idiom for lazy, thread-safe initialization.
 * Blocks reflection-based construction of a second instance.
 * Preserves singleton on serialization via readResolve().
 */
public class MetricsRegistry implements Serializable {

    @Serial
   private static final long serialVersionUID = 1L;
 
   private static volatile boolean instanceCreated = false;

    private final Map<String, Long> counters = new HashMap<>();

    // Static holder — lazy, thread-safe initialization
    private static class Holder {
        private static final MetricsRegistry INSTANCE = new MetricsRegistry();
    }

    // Private constructor — blocks reflection attack
    private MetricsRegistry() {
        if (instanceCreated) {
            throw new IllegalStateException("MetricsRegistry instance already exists! Use getInstance().");
        }
        instanceCreated = true;
    }

    public static MetricsRegistry getInstance() {
        return Holder.INSTANCE;
    }

    // Preserve singleton on deserialization
    @Serial
    private Object readResolve() {
        return getInstance();
    }

    public synchronized void setCount(String key, long value) {
        counters.put(key, value);
    }

    public synchronized void increment(String key) {
        counters.put(key, getCount(key) + 1);
    }

    public synchronized long getCount(String key) {
        return counters.getOrDefault(key, 0L);
    }

    public synchronized Map<String, Long> getAll() {
        return Collections.unmodifiableMap(new HashMap<>(counters));
    }
}
