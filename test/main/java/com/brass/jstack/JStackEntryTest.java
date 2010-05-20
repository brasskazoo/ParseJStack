package com.brass.jstack;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author willdampney
 */
public class JStackEntryTest {
    @Test
    public void testShouldGetContentsAsNotNullWhenNullParameter() throws Exception {
        final JStackEntry stackEntry = new JStackEntry("");
        Assert.assertNotNull(stackEntry.getContents());
    }

    @Test
    public void shouldGetSameContentsAsConstructorParam() throws Exception {
        final JStackEntry stackEntry = new JStackEntry("");
        stackEntry.append("Test Content");

        Assert.assertEquals(12, stackEntry.getContents().length());
        Assert.assertEquals("Test Content", stackEntry.getContents().toString());
    }

    @Test
    public void shouldGetEntryState() throws Exception {
        final JStackEntry stackEntry = new JStackEntry("Thread t@6872: (state = BLOCKED)");
        stackEntry.append("Test Content");

        Assert.assertEquals("BLOCKED", stackEntry.getState());
    }

    @Test
    public void shouldGetUnknownStateWhenHeaderEmpty() throws Exception {
        final JStackEntry stackEntry = new JStackEntry("");
        stackEntry.append("Test Content");

        Assert.assertEquals("UNKNOWN", stackEntry.getState());
    }

    @Test
    public void shouldGetUnknownStateWhenHeaderNull() throws Exception {
        final JStackEntry stackEntry = new JStackEntry(null);
        stackEntry.append("Test Content");

        Assert.assertEquals("UNKNOWN", stackEntry.getState());
    }
}
