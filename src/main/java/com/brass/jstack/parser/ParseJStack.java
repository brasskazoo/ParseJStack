package com.brass.jstack.parser;

import com.brass.jstack.JStackEntry;
import com.brass.jstack.JStackMeta;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author willdampney
 */
public class ParseJStack {
    public ParseJStack(final String filename) {
        File jstackFile = new File(filename);
        if (!jstackFile.exists()) {
            System.out.println("File does not exist. Exiting.");
            return;
        }
        final JStackMeta stackMeta = new Parser(jstackFile).process();

        Map<String, Integer> stateCountMap = new HashMap<String, Integer>();

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
        }

        for (Map.Entry<String, Integer> entry : stateCountMap.entrySet()) {
            System.out.println(entry.getValue() + " threads at " + entry.getKey());
        }
    }


    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Program requires a filename");
            System.exit(1);
        } else {
            new ParseJStack(args[0]);
        }
    }
}
