package br.com.atech.commons.jstack.report;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import br.com.atech.commons.jstack.JStackEntry;
import br.com.atech.commons.jstack.JStackMeta;

/**
 * Process the {@link br.com.atech.commons.jstack.JStackMeta} object and write the results to the console.
 * 
 * @author Will
 */
public class ConsoleReport implements Report {

	@Override
	public void buildReport(final Map<Date, JStackMeta> stackMap) {
		final Map<String, Integer> stateCountMap = new HashMap<>();
		final Map<String, Integer> messageCountMap = new TreeMap<>(); // Sorted
		final Map<JStackEntry, Integer> entryCountMap = new TreeMap<>(); // Sorted

		final Iterator<Date> iterator = stackMap.keySet().iterator();
		while (iterator.hasNext()) {

			final Date date = iterator.next();
			final JStackMeta stackMeta = stackMap.get(date);

			// Report on results
			for (int i = 0; i < stackMeta.getEntries().size(); i++) {
				final JStackEntry entry = stackMeta.getEntries().get(i);

				final String state = entry.getState();

				// State counts
				final Integer count;
				if (stateCountMap.containsKey(state)) {
					count = stateCountMap.get(state) + 1;
				} else {
					count = 1;
				}

				stateCountMap.put(state, count);

				// Detail counts
				final Integer countEntry;
				if (entryCountMap.containsKey(entry)) {
					countEntry = entryCountMap.get(entry) + 1;
				} else {
					countEntry = 1;
				}
				entryCountMap.put(entry, countEntry);

				// One-line summary
				final String contents = entry.getContents();
				final String strStackEnd;
				if (contents.length() != 0) {
					strStackEnd = "(" + state + ") " + contents;
				} else {
					strStackEnd = "(" + state + ") " + "[No stacktrace]";
				}

				final Integer countMessage;
				if (messageCountMap.containsKey(strStackEnd)) {
					countMessage = messageCountMap.get(strStackEnd) + 1;
				} else {
					countMessage = 1;
				}

				messageCountMap.put(strStackEnd, countMessage);
			}
		}

		// State counts
		for (final Map.Entry<String, Integer> entry : stateCountMap.entrySet()) {
			System.out.println(entry.getValue() + " threads at " + entry.getKey());
		}

		System.out.println("\n");

		// Message counts
		for (final Map.Entry<String, Integer> entry : messageCountMap.entrySet()) {
			System.out.println(entry.getValue() + "\tthreads at " + entry.getKey());
		}

		final int limit = 100;
		System.out.println("\n\nPrinting ~" + limit + " chars for each stacktrace\n-----");
		int total = 0;
		for (final Map.Entry<JStackEntry, Integer> entry : entryCountMap.entrySet()) {
			final String state = entry.getKey().getState();
			final String builder = entry.getKey().getContents();
			final int toIndex = builder.lastIndexOf("\n", limit);
			String output;
			if (toIndex > 0) {
				output = "\t\t" + builder.substring(0, toIndex);
			} else {
				output = "\t\t[No stacktrace]";
			}

			System.out.println(entry.getValue() + "\t" + state + " threads at \n" + output.replaceAll("\n", "\n\t\t"));
			total += entry.getValue();
		}
		System.out.println("\ntotal entries = " + total);
	}
}