package br.com.atech.commons.jstack;

import org.junit.Assert;
import org.junit.Test;

import br.com.atech.commons.jstack.JStackEntry;
import br.com.atech.commons.jstack.JStackMeta;

/**
 * @author Will
 */
public class JStackMetaTest {
	@Test
	public void testAddEmptyEntry() throws Exception {
		final JStackMeta stackMeta = JStackMeta.create(null);
		stackMeta.addEntry(new JStackEntry(""));
		Assert.assertEquals(1, stackMeta.getEntries().size());
	}

	@Test
	public void testAddEntry() throws Exception {
		final JStackMeta stackMeta = JStackMeta.create(null);
		final JStackEntry stackEntry = new JStackEntry("");
		stackEntry.append("Test Entry");
		stackMeta.addEntry(stackEntry);
		Assert.assertEquals(1, stackMeta.getEntries().size());
	}

	@Test
	public void testAddNullEntry() throws Exception {
		final JStackMeta stackMeta = JStackMeta.create(null);
		stackMeta.addEntry(null);
		Assert.assertEquals(0, stackMeta.getEntries().size());
	}

	@Test
	public void testAppend() throws Exception {
		final JStackMeta stackMeta = JStackMeta.create("Test Meta");
		Assert.assertEquals(9, stackMeta.getHeader().length());
	}

	@Test
	public void testConstructorInit() throws Exception {
		Assert.assertNotNull(JStackMeta.create(null).getHeader());
		Assert.assertNotNull(JStackMeta.create(null).getEntries());
	}
}
