package com.brass.jstack.parser;

import java.io.*;

/**
 * @author willdampney
 */
public class Parser {
    private final File _file;

    public Parser(final File file) {
        _file = file;
    }

    public void process() {
        try {
            FileInputStream fstream;
            fstream = new FileInputStream(_file);

            DataInputStream in = new DataInputStream(fstream);

            while (in.available() !=0)
            {
                // Print file line to screen
                System.out.println (in.readLine());
            }

            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }        
    }
}
