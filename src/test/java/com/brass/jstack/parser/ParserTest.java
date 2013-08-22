package com.brass.jstack.parser;

import java.io.File;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

import com.brass.jstack.JStackMeta;

/**
 * @author Will
 */
public class ParserTest {
	@Test
	public void testProcess() throws Exception {
		final URL resource = getClass().getResource("/test.jstack");
		final File file = new File(resource.getFile());

		final JStackMeta stackMeta = new Parser(file).process();

		Assert.assertEquals(94, stackMeta.getHeader().length());
		Assert.assertEquals(432, stackMeta.getEntries().size());
	}
}
