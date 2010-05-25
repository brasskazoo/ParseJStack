package com.brass.jstack.parser;

import com.brass.jstack.JStackEntry;
import com.brass.jstack.JStackMeta;

import java.io.*;

/**
 * Provides the parsing engine to extract the information from the JStack output.
 *
 * @author Will
 */
public class Parser {
    private final File _file;
    private final JStackMeta _meta;

    public Parser(final File file) {
        _file = file;
        _meta = new JStackMeta();
    }

    /**
     * Process the JStack output file and extract the data into a {@link com.brass.jstack.JStackMeta} object.
     *
     * @return The {@link com.brass.jstack.JStackMeta} object representing the JStack output.
     */
    public JStackMeta process() {
        try {
            FileInputStream inputStream;
            inputStream = new FileInputStream(_file);

            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

            boolean finishedHeader = false;
            JStackEntry currentEntry = new JStackEntry("");

            String line;
            while ((line = in.readLine()) != null)
            {
                // Skip blanks
                if ("".equals(line.trim())) {
                    continue;
                }

                line += "\n";

                // Check if we're done with the header lines
                if (!finishedHeader && line.startsWith("Thread")) {
                    finishedHeader = true;
                }

                if (!finishedHeader) {
                    _meta.append(line);
                    continue;
                }

                if (line.startsWith("Thread")) {
                    currentEntry = new JStackEntry(line);
                    _meta.addEntry(currentEntry);
                } else {
                    currentEntry.append(line);
                }

            }

            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File was not found");
        } catch (IOException e) {
            System.out.println("ERROR: A problem occurred");
            e.printStackTrace(); 
        }

        return _meta;
    }
}
