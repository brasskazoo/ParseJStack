package com.brass.jstack.report;

import com.brass.jstack.JStackEntry;
import com.brass.jstack.JStackMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Process the {@link com.brass.jstack.JStackMeta} object and write the results to the console.
 * 
 * @author Will
 */
public class ConsoleReport implements Report {

    public void buildReport(final JStackMeta stackMeta) {
        Map<String, Integer> stateCountMap = new HashMap<String, Integer>();
        Map<String, Integer> messageCountMap = new TreeMap<String, Integer>();  // Sorted

        Map<JStackEntry, Integer> entryCountMap = new TreeMap<JStackEntry, Integer>(); // Sorted

        // Report on results
        for (int i = 0; i < stackMeta.getEntries().size(); i++) {
            JStackEntry entry = stackMeta.getEntries().get(i);

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
            final StringBuilder contents = entry.getContents();
            final String strStackEnd;
            if (contents.length() != 0) {
                strStackEnd = "(" + state + ") " + contents.substring(0, contents.indexOf("\n"));
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

        // State counts
        for (Map.Entry<String, Integer> entry : stateCountMap.entrySet()) {
            System.out.println(entry.getValue() + " threads at " + entry.getKey());
        }

        System.out.println("\n");

        // Message counts
        for (Map.Entry<String, Integer> entry : messageCountMap.entrySet()) {
            System.out.println(entry.getValue() + "\tthreads at " + entry.getKey());
        }

        final int limit = 600;
        System.out.println("\n\nPrinting ~" + limit + " chars for each stacktrace\n-----");
        int total = 0;
        for (Map.Entry<JStackEntry, Integer> entry : entryCountMap.entrySet()) {
            final String state = entry.getKey().getState();
            final StringBuilder builder = entry.getKey().getContents();
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