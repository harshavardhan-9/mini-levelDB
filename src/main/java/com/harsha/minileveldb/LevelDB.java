package com.harsha.minileveldb;

import java.io.IOException;
import java.util.List;

public interface LevelDB {
    void put(String key, String value) throws IOException;

    String get(String key) throws IOException;

    void delete(String key) throws IOException;

    List<Entry> scan(String startKey, String endKey) throws IOException;
}
