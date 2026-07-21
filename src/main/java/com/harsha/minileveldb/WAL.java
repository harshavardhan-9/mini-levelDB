package com.harsha.minileveldb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WAL {
    private final String path;
    private final FileWriter writer;

    public WAL(String path) throws IOException {
        this.path = path;
        this.writer = new FileWriter(path, true);
    }

    public void log(Entry entry) throws IOException {
        String op = entry.deleted ? "DEL" : "PUT";
        String value = entry.value != null ? entry.value : "";
        writer.write(entry.sequenceNumber + "|" + op + "|" + entry.key + "|" + value + "\n");
        writer.flush();
    }

    public List<Entry> replay() throws IOException {
        List<Entry> entries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 4);
                long seq = Long.parseLong(parts[0]);
                boolean deleted = parts[1].equals("DEL");
                String value = deleted ? null : parts[3];
                entries.add(new Entry(parts[2], value, deleted, seq));
            }
        }
        return entries;
    }

    public void clear() throws IOException {
        new FileWriter(path, false).close();
    }
}
