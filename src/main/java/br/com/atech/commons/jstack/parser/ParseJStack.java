package br.com.atech.commons.jstack.parser;

import java.io.File;

import br.com.atech.commons.jstack.report.ConsoleReport;
import br.com.atech.commons.jstack.report.Report;

/**
 * Currently, the program handles one argument, which is the path to a jstack output file.
 * 
 * @author Will
 */
public class ParseJStack {

	private static int FILE_NAME = 0;

	public static void main(final String[] args) {
		if (args.length < 1) {
			printProgramUsage();
			System.exit(1);
		}

		final ParseJStack parser = new ParseJStack(args[FILE_NAME]);
		// if(args[x] == 'printReport')
		parser.buildReport();
	}

	public static void printProgramUsage() {

		final StringBuilder sb = new StringBuilder();
		sb.append("java -jar <jar_file> [args...]\n")//
				.append("where args include:\n")//
				.append("\t1 - jstack dump file name");

		System.out.println(sb.toString());
	}

	private final Report report = new ConsoleReport();
	private JStackParser jstackParser;

	public ParseJStack(final String filename) {
		final File jstackFile = new File(filename);
		if (!jstackFile.exists()) {
			System.out.println(new StringBuilder("File ").append(filename).append(" does not exist. Exiting...").toString());
			return;
		}

		jstackParser = new JStackParser(jstackFile);
	}

	private void buildReport() {
		report.buildReport(jstackParser.getJStackMetaMap());
	}
}
