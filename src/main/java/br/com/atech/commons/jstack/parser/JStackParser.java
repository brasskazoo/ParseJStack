package br.com.atech.commons.jstack.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.atech.commons.jstack.JStackEntry;
import br.com.atech.commons.jstack.JStackMeta;

/**
 * Provides the parsing engine to extract the information from the JStack output.
 * 
 * @author Will
 */
public class JStackParser {

	private final File _file;
	private final Map<Date, JStackMeta> metaMap;

	public JStackParser(final File file) {
		_file = file;
		metaMap = new HashMap<>();
		process();
	}

	public Map<Date, JStackMeta> getJStackMetaMap() {
		return Collections.unmodifiableMap(metaMap);
	}

	private boolean isNewThreadDump(final String ln) {
		return JStackPatterns.THREAD_DUMP_DATE_PATTERN.matcher(ln).find();
	}

	private boolean isNewThreadStart(final String line) {
		return JStackPatterns.THREAD_NAME_PATTERN.matcher(line).find();
	}

	/**
	 * Process the JStack output file and extract the data into a
	 * {@link br.com.atech.commons.jstack.JStackMeta} object.
	 * 
	 * @return The {@link br.com.atech.commons.jstack.JStackMeta} object representing the JStack output.
	 */
	private void process() {

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(_file)))) {

			StringBuilder headerBuilder = new StringBuilder();

			boolean finishedHeader = false;

			JStackEntry currentEntry = null;
			JStackMeta currentMeta = null;

			String line;
			while ((line = reader.readLine()) != null) {
				if ("".equals(line)) {
					continue;
				}

				if (isNewThreadDump(line)) {
					finishedHeader = false;
					headerBuilder = new StringBuilder(line);
				}

				final boolean newThreadStart = isNewThreadStart(line);
				if (!finishedHeader && newThreadStart) {
					finishedHeader = true;
					currentMeta = JStackMeta.create(headerBuilder.toString().trim());
					metaMap.put(currentMeta.getStackDate(), currentMeta);
				}

				if (!finishedHeader) {
					headerBuilder.append(line).append("\n");
					continue;
				}

				if (newThreadStart) {
					currentEntry = new JStackEntry(line);
					currentMeta.addEntry(currentEntry);
				} else {
					currentEntry.append(line);
				}
			}

		} catch (final FileNotFoundException e) {
			System.err.println("ERROR: " + e.getMessage());
		} catch (final IOException e) {
			System.err.println("ERROR: A problem occurred: " + e.getMessage());
		}
	}
}
