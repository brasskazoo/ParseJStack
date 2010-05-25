package com.brass.jstack;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Will
 */
public class JStackEntryTest {
    @Test
    public void testShouldGetContentsAsNotNullWhenNullParameter() throws Exception {
        final JStackEntry stackEntry = new JStackEntry("");
        Assert.assertNotNull(stackEntry.getContents());
    }

    @Test
    public void shouldGetContents() throws Exception {
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
    public void shouldGetUnknownStateWhenHeaderUnrecognised() throws Exception {
        final JStackEntry stackEntry = new JStackEntry("Thread t@6872:");
        stackEntry.append("Test Content");

        Assert.assertEquals("UNKNOWN", stackEntry.getState());
    }

    @Test
    public void shouldGetUnknownStateWhenHeaderNull() throws Exception {
        final JStackEntry stackEntry = new JStackEntry(null);
        stackEntry.append("Test Content");

        Assert.assertEquals("UNKNOWN", stackEntry.getState());
    }

    @Test
    public void testEqualStatesAreEqualObjects() {
        final JStackEntry stackEntry1 = new JStackEntry("Thread t@5678: (state = BLOCKED)");
        final JStackEntry stackEntry2 = new JStackEntry("Thread t@1234: (state = BLOCKED)");

        Assert.assertEquals(stackEntry1.hashCode(), stackEntry2.hashCode());
        Assert.assertEquals(stackEntry1, stackEntry2);
    }

    @Test
    public void testEqualContentsAreEqualObjects() {
        final JStackEntry stackEntry1 = new JStackEntry("Thread t@5678: (state = BLOCKED)");
        final JStackEntry stackEntry2 = new JStackEntry("Thread t@1234: (state = BLOCKED)");
        stackEntry1.append("Test Content 1");
        stackEntry1.append("Test Content 2");
        stackEntry2.append("Test Content 1");
        stackEntry2.append("Test Content 2");

        Assert.assertEquals(stackEntry1.hashCode(), stackEntry2.hashCode());
        Assert.assertEquals(stackEntry1, stackEntry2);
    }

    @Test
    public void shouldHaveDifferentHashStateOnly() {
        final JStackEntry stackEntry1 = new JStackEntry("Thread t@5678: (state = BLOCKED)");
        final JStackEntry stackEntry2 = new JStackEntry("Thread t@1234: (state = IN_NATIVE)");

        Assert.assertNotSame(stackEntry1.hashCode(), stackEntry2.hashCode());
        Assert.assertNotSame(stackEntry1, stackEntry2);
    }

    @Test
    public void shouldHaveDifferentHash() {
        final JStackEntry stackEntry1 = new JStackEntry("Thread t@6872: (state = BLOCKED)");
        stackEntry1.append("Test Content 1");
        stackEntry1.append("Test Content 2");
        final JStackEntry stackEntry2 = new JStackEntry("Thread t@6872: (state = BLOCKED)");
        stackEntry2.append("Test Content 1");
        stackEntry2.append("Test Content 2");
        stackEntry2.append("Test Content 3");

        Assert.assertNotSame(stackEntry1.hashCode(), stackEntry2.hashCode());
        Assert.assertNotSame(stackEntry1, stackEntry2);
    }

    @Test
    public void testEqualStatesAreComparable() {
        final JStackEntry stackEntry1 = new JStackEntry("Thread t@5678: (state = BLOCKED)");
        final JStackEntry stackEntry2 = new JStackEntry("Thread t@1234: (state = BLOCKED)");

        Assert.assertEquals(0, stackEntry1.compareTo(stackEntry2));
    }

    @Test
    public void testUnequalContentsAreComparable() {
        final JStackEntry stackEntry1 = new JStackEntry("Thread t@5678: (state = BLOCKED)");
        stackEntry1.append("Test Content 1");
        final JStackEntry stackEntry2 = new JStackEntry("Thread t@1234: (state = BLOCKED)");
        stackEntry2.append("Test Content 1");
        stackEntry2.append("Test Content 2");

        Assert.assertEquals(1, stackEntry1.compareTo(stackEntry2));
    }

    @Test
    public void testEmptyContentsAreComparable() {
        final JStackEntry stackEntry1 = new JStackEntry("Thread t@5678: (state = BLOCKED)");
        final JStackEntry stackEntry2 = new JStackEntry("Thread t@1234: (state = BLOCKED)");
        stackEntry2.append("Test Content 1");

        Assert.assertEquals(-1, stackEntry1.compareTo(stackEntry2));
    }
}
