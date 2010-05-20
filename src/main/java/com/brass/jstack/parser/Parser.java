package com.brass.jstack.parser;

import com.brass.jstack.JStackEntry;
import com.brass.jstack.JStackMeta;

import java.io.*;

/**
 * @author willdampney
 */
public class Parser {
    private final File _file;
    private final JStackMeta _meta;

    public Parser(final File file) {
        _file = file;
        _meta = new JStackMeta();
    }

    public JStackMeta process() {
        try {
            FileInputStream fstream;
            fstream = new FileInputStream(_file);

            BufferedReader in = new BufferedReader(new InputStreamReader(fstream));

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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return _meta;
    }
}
