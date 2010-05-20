package com.brass.jstack;

/**
 * Contains the individual thread dump of a JStack trace
 * 
 * @author willdampney
 */
public class JStackEntry {
    final private StringBuilder _contents;

    public JStackEntry(final StringBuilder contents) {
        if (contents == null) {
            _contents = new StringBuilder();
        } else {
            _contents = contents;
        }
    }

    public StringBuilder getContents() {
        return _contents;
    }
}
