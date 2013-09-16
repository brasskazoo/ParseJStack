package br.com.atech.commons.jstack.parser;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import br.com.atech.commons.jstack.JStackMeta;
import br.com.atech.commons.jstack.parser.JStackParser;
import br.com.atech.commons.jstack.report.ConsoleReport;

/**
 * @author Will
 */
public class JStackParserTest {
	@Test
	public void testProcess() throws Exception {
		final URL resource = Thread.currentThread().getContextClassLoader().getResource("test.jstack");
		final File file = new File(resource.getFile());

		final JStackParser jStackParser = new JStackParser(file);

		new ConsoleReport().buildReport(jStackParser.getJStackMetaMap());

		final Map<Date, JStackMeta> map = jStackParser.getJStackMetaMap();

		final Iterator<Date> it = map.keySet().iterator();
		final JStackMeta meta = map.get(it.next());
		Assert.assertEquals(112, meta.getHeader().length());
		Assert.assertEquals(432, meta.getEntries().size());
	}
}
