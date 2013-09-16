package br.com.atech.commons.jstack;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

import br.com.atech.commons.jstack.parser.JStackPatterns;

/**
 * Container for data extracted from a JStack output file.
 * 
 * @author Will
 */
public class JStackMeta {

	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static JStackMeta create(final String ln) {
		final Date date = getDumpDate(ln);
		return new JStackMeta(ln == null ? "" : ln, date);
	}

	public static Date getDumpDate(final String ln) {

		if (ln == null || "".equals(ln.trim())) {
			return new Date();
		}

		final Matcher matcher = JStackPatterns.THREAD_DUMP_DATE_PATTERN.matcher(ln);
		if (matcher.find()) {
			try {
				final String dateStr = matcher.group();
				return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).parse(dateStr);
			} catch (final ParseException e) {
				System.out.println(e.getMessage());
			}
		}
		return null;
	}

	private final Date stackDate;
	private final String header;

	private final List<JStackEntry> entries;

	private JStackMeta(final String header, final Date date) {
		this.header = header;
		stackDate = date;
		entries = new ArrayList<JStackEntry>();
	}

	public void addEntry(final JStackEntry jStackEntry) {
		if (jStackEntry == null) {
			return;
		}
		entries.add(jStackEntry);
	}

	public List<JStackEntry> getEntries() {
		return entries;
	}

	public String getHeader() {
		return header;
	}

	public Date getStackDate() {
		return stackDate;
	}
}
