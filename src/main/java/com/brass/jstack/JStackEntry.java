package com.brass.jstack;

/**
 * Contains the individual thread dump of a JStack trace
 * 
 * @author willdampney
 */
public class JStackEntry {
    final private StringBuilder _contents;

    public JStackEntry() {
        _contents = new StringBuilder();
    }

    public StringBuilder getContents() {
        return _contents;
    }

    public void append(String line) {
        _contents.append(line);
    }
}
