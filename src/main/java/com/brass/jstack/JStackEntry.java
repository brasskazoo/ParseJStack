package com.brass.jstack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains the individual thread dump of a JStack trace, and the header line.
 *
 * The header line contains the thread state (blocked, in_native, etc), and the <code>getState()</code> function performs the processing to extract the thread state.
 * 
 * @author Will
 */
public class JStackEntry {
    final private StringBuilder _contents;
    private final String _header;

    public JStackEntry(final String line) {
        //TODO handle null/empty
        _header = line;
        _contents = new StringBuilder();
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
