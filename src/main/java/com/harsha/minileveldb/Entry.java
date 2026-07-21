package com.harsha.minileveldb;

public class Entry {
    final String key;
    final String value;
    final boolean deleted;
    final long sequenceNumber;

    public Entry(String key, String value, boolean deleted, long sequenceNumber) {
        this.key = key;
        this.value = value;
        this.deleted = deleted;
        this.sequenceNumber = sequenceNumber;
    }
}
