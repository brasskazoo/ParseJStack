package com.brass.jstack;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Will
 */
public class JStackMetaTest {
    @Test
    public void testConstructorInit() throws Exception {
        Assert.assertNotNull(new JStackMeta().getHeader());
        Assert.assertNotNull(new JStackMeta().getEntries()); 
    }

    @Test
    public void testAppend() throws Exception {
        final JStackMeta stackMeta = new JStackMeta();
        stackMeta.append("Test Meta");
        Assert.assertEquals(9, stackMeta.getHeader().length());
    }

    @Test
    public void testAddNullEntry() throws Exception {
        final JStackMeta stackMeta = new JStackMeta();
        stackMeta.addEntry(null);
        Assert.assertEquals(0, stackMeta.getEntries().size());
    }


    @Test
    public void testAddEmptyEntry() throws Exception {
        final JStackMeta stackMeta = new JStackMeta();
        stackMeta.addEntry(new JStackEntry(""));
        Assert.assertEquals(1, stackMeta.getEntries().size());
    }


    @Test
    public void testAddEntry() throws Exception {
        final JStackMeta stackMeta = new JStackMeta();
        final JStackEntry stackEntry = new JStackEntry("");
        stackEntry.append("Test Entry");
        stackMeta.addEntry(stackEntry);
        Assert.assertEquals(1, stackMeta.getEntries().size());
    }
}
