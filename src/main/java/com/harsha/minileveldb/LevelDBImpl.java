package com.harsha.minileveldb;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelDBImpl implements LevelDB {
    private final MemTable memTable;
    private final WAL wal;
    private long sequenceNumber = 0;

    public LevelDBImpl(String dataDir) throws IOException {
        new File(dataDir).mkdirs();
        this.wal = new WAL(dataDir + "/wal.log");
        this.memTable = new MemTable(1000);
    }

    @Override
    public void put(String key, String value) throws IOException {
        Entry e = new Entry(key, value, false, ++sequenceNumber);
        wal.log(e);
        memTable.put(key, value, sequenceNumber);
        maybeFlush();
    }

    @Override
    public void delete(String key) throws IOException {
        Entry e = new Entry(key, null, true, ++sequenceNumber);
        wal.log(e);
        memTable.delete(key, sequenceNumber);
        maybeFlush();
    }

    @Override
    public String get(String key) throws IOException {
        Entry e = memTable.get(key);
        if (e == null) return null;
        return e.deleted ? null : e.value;
    }

    @Override
    public List<Entry> scan(String startKey, String endKey) throws IOException {
        return new ArrayList<>(memTable.scan(startKey, endKey).values());
    }

    private void maybeFlush() {
        if (memTable.shouldFlush()) {
            // ponytail: real SSTable flush + wal.clear() arrive Day 5.
            // For now this is just the trigger point — flushing without
            // persisting to an SSTable first would destroy data.
            System.out.println("[maybeFlush] threshold reached, size=" + memTable.size());
        }
    }
}
