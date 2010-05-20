package com.brass.jstack.parser;

import com.brass.jstack.JStackMeta;
import com.brass.jstack.report.ConsoleReport;
import com.brass.jstack.report.Report;

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
        final JStackMeta stackMeta = new Parser(jstackFile).process();

        final Report report = new ConsoleReport();
        report.buildReport(stackMeta);
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
