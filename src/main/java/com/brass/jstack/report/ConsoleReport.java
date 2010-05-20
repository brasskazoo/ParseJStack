package com.brass.jstack.report;

import com.brass.jstack.JStackEntry;
import com.brass.jstack.JStackMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ConsoleReport implements Report {

    public void buildReport(final JStackMeta stackMeta) {
        Map<String, Integer> stateCountMap = new HashMap<String, Integer>();
        Map<String, Integer> messageCountMap = new TreeMap<String, Integer>();  // Sorted

        // Report on results
        for (int i = 0; i < stackMeta.getEntries().size(); i++) {
            JStackEntry entry = stackMeta.getEntries().get(i);

            final String state = entry.getState();

            final Integer count;
            if (stateCountMap.containsKey(state)) {
                count = stateCountMap.get(state) + 1;
            } else {
                count = 1;
            }

            stateCountMap.put(state, count);


            final StringBuilder contents = entry.getContents();
            if (contents.length() != 0) {
                final String strStackEnd = "(" + state + ") " + contents.substring(0, contents.indexOf("\n"));

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
        for (Map.Entry<String, Integer> entry : stateCountMap.entrySet()) {
            System.out.println(entry.getValue() + " threads at " + entry.getKey());
        }

        System.out.println("\n");

        // Message counts
        for (Map.Entry<String, Integer> entry : messageCountMap.entrySet()) {
            System.out.println(entry.getValue() + "\tthreads at " + entry.getKey());
        }
    }
}