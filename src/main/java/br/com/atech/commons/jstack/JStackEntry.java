package br.com.atech.commons.jstack;

import java.util.Objects;
import java.util.regex.Matcher;

import br.com.atech.commons.jstack.parser.JStackPatterns;

/**
 * Contains the individual thread dump of a JStack trace, and the header line. The header line
 * contains the thread state (blocked, in_native, etc), and the <code>getState()</code> function
 * performs the processing to extract the thread state.
 * 
 * @author Will
 */
public class JStackEntry implements Comparable<JStackEntry> {

	private static final String UNKNOWN_THREAD_ID = "Unknown Thread ID";
	private static final String UNKNOWN_THREAD_LABEL = "Unknown Thread Label";

	private final StringBuilder contents;
	private final String header;

	private String state;
	private final String threadId;
	private final String threadLabel;

	public JStackEntry(final String line) {
		header = Objects.requireNonNull(line, "JStack entry line cannot be null");
		contents = new StringBuilder();
		threadLabel = parseJStackEntryThreadLabel();
		threadId = parseJStackEntryThreadId();
	}

	public void append(final String line) {
		contents.append(line);
	}

	@Override
	public int compareTo(final JStackEntry o) {

		if (equals(o)) {
			return 0;
		} else if (state == o.state) {
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

		if (contents == null) {
			if (other.contents != null) {
				return false;
			}
		} else if (!getContents().equals(other.getContents())) {
			return false;
		}

		if (header == null) {
			if (other.header != null) {
				return false;
			}
		} else if (!header.equals(other.header)) {
			return false;
		}
		if (state != other.state) {
			return false;
		}
		return true;
	}

	public String getContents() {
		return contents.toString();
	}

	/**
	 * Extract the thread state from the header string
	 * 
	 * @return The reported state, or 'UNKNOWN' if it is not found.
	 */
	public String getState() {

		if (state == null) {
			state = parseJStackEntryState();
		}

		return state;
	}

	public String getThreadId() {
		return threadId;
	}

	public String getThreadLabel() {
		return threadLabel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (contents == null ? 0 : contents.hashCode());
		result = prime * result + (header == null ? 0 : header.hashCode());
		result = prime * result + (state == null ? 0 : state.hashCode());
		return result;
	}

	private String parseJStackEntryState() {
		String st = "UNKNOWN";
		final Matcher m = JStackPatterns.STATE_PATTERN.matcher(contents);
		if (m.find()) {
			if (m.groupCount() > 0) {
				st = m.group(1);
			}
		}

		return st;
	}

	private String parseJStackEntryThreadId() {

		final Matcher matcher = JStackPatterns.THREAD_ID_PATTERN.matcher(header);
		if (matcher.find()) {
			return matcher.group().split("=")[1];
		}

		return UNKNOWN_THREAD_ID;
	}

	private String parseJStackEntryThreadLabel() {
		final Matcher matcher = JStackPatterns.THREAD_NAME_PATTERN.matcher(header);
		if (matcher.find()) {
			return matcher.group();
		}

		return UNKNOWN_THREAD_LABEL;
	}

	@Override
	public String toString() {
		return String.format("JStackEntry: state=%s\ncontents=%s", getState(), getContents().length() > 10 ? getContents().substring(0, 10) : getContents());
	}
}
