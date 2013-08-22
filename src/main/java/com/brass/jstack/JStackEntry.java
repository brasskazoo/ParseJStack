package com.brass.jstack;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains the individual thread dump of a JStack trace, and the header line. The header line
 * contains the thread state (blocked, in_native, etc), and the <code>getState()</code> function
 * performs the processing to extract the thread state.
 * 
 * @author Will
 */
public class JStackEntry implements Comparable<JStackEntry> {

	private static final String STATE_REGEX = "\\s*java.lang.Thread.State:\\s+(\\w+)\\s*\\(*.*\\)*";
	private static final Pattern STATE_PATTERN = Pattern.compile(STATE_REGEX);

	private static final String CALL_REGEX = "\\s+at\\s+(.*)";
	private static final Pattern CALL_PATTERN = Pattern.compile(CALL_REGEX);

	private final StringBuilder _contents;
	private final String _header;

	private final Thread.State _state;

	public JStackEntry(final String line) {
		_header = Objects.requireNonNull(line, "JStack entry line cannot be null");
		_contents = new StringBuilder();
		_state = extractJStackEntryState();
	}

	public void append(final String line) {
		_contents.append(line);
	}

	@Override
	public int compareTo(final JStackEntry o) {

		if (equals(o)) {
			return 0;
		} else if (getState().equals(o.getState())) {
			return getContents().compareTo(o.getContents());
		} else {
			return getState().compareTo(o.getState());
		}
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof JStackEntry)) {
			return false;
		}
		final JStackEntry other = (JStackEntry) obj;
		if (_contents == null) {
			if (other._contents != null) {
				return false;
			}
		} else if (!getContents().equals(other.getContents())) {
			return false;
		}
		if (_header == null) {
			if (other._header != null) {
				return false;
			}
		} else if (!_header.equals(other._header)) {
			return false;
		}
		if (_state != other._state) {
			return false;
		}
		return true;
	}

	private Thread.State extractJStackEntryState() {
		Thread.State st = null;
		final Matcher m = STATE_PATTERN.matcher(_header);
		if (m.find()) {
			if (m.groupCount() > 0) {
				final String strState = m.group(1);
				st = Thread.State.valueOf(strState);
			}
		}
		return st;
	}

	public String getContents() {
		return _contents.toString();
	}

	/**
	 * Extract the thread state from the header string
	 * 
	 * @return The reported state, or 'UNKNOWN' if it is not found.
	 */
	public Thread.State getState() {
		return _state;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (_contents == null ? 0 : _contents.hashCode());
		result = prime * result + (_header == null ? 0 : _header.hashCode());
		result = prime * result + (_state == null ? 0 : _state.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return String.format("JStackEntry: state=%s\ncontents=%s", getState().name(), getContents().length() > 10 ? getContents().substring(0, 10) : getContents());
	}
}
