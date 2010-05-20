package com.brass.jstack.parser;

import java.io.File;

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
        new Parser(jstackFile).process();
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
