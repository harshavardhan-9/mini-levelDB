package com.harsha.minileveldb;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

public class MemTable {
    private final TreeMap<String, Entry> table = new TreeMap<>();
    private final int flushThreshold;

    public MemTable(int flushThreshold) {
        this.flushThreshold = flushThreshold;
    }

    public void put(String key, String value, long seq) {
        table.put(key, new Entry(key, value, false, seq));
    }

    public void delete(String key, long seq) {
        table.put(key, new Entry(key, null, true, seq));
    }

    public Entry get(String key) {
        return table.get(key);
    }

    public SortedMap<String, Entry> scan(String startKey, String endKey) {
        return table.subMap(startKey, true, endKey, true);
    }

    public boolean shouldFlush() {
        return table.size() >= flushThreshold;
    }

    public Collection<Entry> sortedEntries() {
        return table.values();
    }

    public void clear() {
        table.clear();
    }

    public int size() {
        return table.size();
    }
}
