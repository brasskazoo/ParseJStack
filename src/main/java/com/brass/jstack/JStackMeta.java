package com.brass.jstack;

import java.util.ArrayList;
import java.util.List;

/**
 * Container for data extracted from a JStack output file.
 * 
 * @author Will
 */
public class JStackMeta {
    private StringBuilder _header;
    private List<JStackEntry> _entries;

    public JStackMeta() {
        _header = new StringBuilder(4);
        _entries = new ArrayList<JStackEntry>();
    }

    public StringBuilder getHeader() {
        return _header;
    }

    public List<JStackEntry> getEntries() {
        return _entries;
    }

    public void append(final String line) {
        _header.append(line);
    }

    public void addEntry(final JStackEntry jStackEntry) {
        if (jStackEntry == null) {
            return;
        }
        _entries.add(jStackEntry);
    }
}
