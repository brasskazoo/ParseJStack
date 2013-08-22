package com.brass.jstack.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import com.brass.jstack.JStackEntry;
import com.brass.jstack.JStackMeta;

/**
 * Provides the parsing engine to extract the information from the JStack output.
 * 
 * @author Will
 */
public class Parser {
	private final File _file;
	private final JStackMeta _meta;

	private static final String THREAD_NAME_REGEX = "\\\"([^\"]+)\\\".*";
	private static final Pattern THREAD_NAME_PATTERN = Pattern.compile(THREAD_NAME_REGEX);

	public Parser(final File file) {
		_file = file;
		_meta = new JStackMeta();
	}

	public boolean isNewThreadStart(final String line) {
		return THREAD_NAME_PATTERN.matcher(line).find();
	}

	/**
	 * Process the JStack output file and extract the data into a
	 * {@link com.brass.jstack.JStackMeta} object.
	 * 
	 * @return The {@link com.brass.jstack.JStackMeta} object representing the JStack output.
	 */
	public JStackMeta process() {
		try {
			FileInputStream inputStream;
			inputStream = new FileInputStream(_file);

			final BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

			boolean finishedHeader = false;
			JStackEntry currentEntry = new JStackEntry("");

			String line;
			while ((line = in.readLine()) != null) {
				// Skip blanks
				if ("".equals(line.trim())) {
					continue;
				}

				line += "\n";

				// Check if we're done with the header lines
				if (!finishedHeader && isNewThreadStart(line)) {
					finishedHeader = true;
				}

				if (!finishedHeader) {
					_meta.append(line);
					continue;
				}

				if (isNewThreadStart(line)) {
					currentEntry = new JStackEntry(line);
					_meta.addEntry(currentEntry);
				} else {
					currentEntry.append(line);
				}

			}

			in.close();
		} catch (final FileNotFoundException e) {
			System.out.println("ERROR: File was not found");
		} catch (final IOException e) {
			System.out.println("ERROR: A problem occurred");
			e.printStackTrace();
		}

		return _meta;
	}
}
