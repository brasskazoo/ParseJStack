package br.com.atech.commons.jstack.parser;

import java.util.regex.Pattern;

public class JStackPatterns {

	// 2013-08-16 02:57:32
	private static final String THREAD_DUMP_DATE_REGEX = "((\\d{4})\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])\\s(\\d{2})\\:(\\d{2})\\:(\\d{2}))";
	public static final Pattern THREAD_DUMP_DATE_PATTERN = Pattern.compile(THREAD_DUMP_DATE_REGEX);

	private static final String THREAD_NAME_REGEX = "\\\"([^\"]+)\\\".*";
	public static final Pattern THREAD_NAME_PATTERN = Pattern.compile(THREAD_NAME_REGEX);

	private static final String STATE_REGEX = "\\s*java.lang.Thread.State:\\s+(\\w+)\\s*\\(.*\\)*";
	public static final Pattern STATE_PATTERN = Pattern.compile(STATE_REGEX);

	private static final String CALL_REGEX = "\\s+at\\s+(.*)";
	public static final Pattern CALL_PATTERN = Pattern.compile(CALL_REGEX);

	private static final String THREAD_ID_REGEX = "\\s(tid=\\w+)\\s";
	public static final Pattern THREAD_ID_PATTERN = Pattern.compile(THREAD_ID_REGEX);

}