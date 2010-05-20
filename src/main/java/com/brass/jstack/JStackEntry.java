package com.brass.jstack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains the individual thread dump of a JStack trace
 * 
 * @author willdampney
 */
public class JStackEntry {
    final private StringBuilder _contents;
    private final String _header;

    public JStackEntry(final String line) {
        //TODO handle null/empty
        _header = line;
        _contents = new StringBuilder();
    }

    public String getHeader() {
        return _header;
    }

    public StringBuilder getContents() {
        return _contents;
    }

    public void append(String line) {
        _contents.append(line);
    }

    /**
     * Extract the thread state from the header string
     *
     * @return The reported state, or 'UNKNOWN' if it is not found.
     */
    public String getState() {
        if (_header == null || _header.length() == 0) {
            return "UNKNOWN";
        }

        Matcher m = Pattern.compile(".*\\(state = (\\w+)\\).*").matcher(_header);

        m.find();

        final String strState;
        if (m.groupCount() > 0) {
            strState = m.group(1);
        } else {
            strState = "UNKNOWN";
        }

        return strState;
    }
}
