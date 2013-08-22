package com.brass.jstack;

import static com.brass.jstack.JStackEntry.JStackEntryState.BLOCKED;
import static com.brass.jstack.JStackEntry.JStackEntryState.UNKNOWN;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Will
 */
public class JStackEntryTest {
	@Test
	public void shouldGetContents() throws Exception {
		final JStackEntry stackEntry = new JStackEntry("");
		stackEntry.append("Test Content");

		Assert.assertEquals(12, stackEntry.getContents().length());
		Assert.assertEquals("Test Content", stackEntry.getContents().toString());
	}

	@Test
	public void shouldGetEntryState() throws Exception {
		final JStackEntry stackEntry = new JStackEntry("java.lang.Thread.State: BLOCKED (on object monitor)");
		stackEntry.append("Test Content");

		Assert.assertEquals(BLOCKED, stackEntry.getState());
	}

	@Test(expected = NullPointerException.class)
	public void shouldGetNullPointerExceptionWhenHeaderIsNull() throws Exception {
		new JStackEntry(null);
		Assert.fail("Should have thrown a NullPointerException");
	}

	@Test
	public void shouldGetUnknownStateWhenHeaderEmpty() throws Exception {
		final JStackEntry stackEntry = new JStackEntry("");
		stackEntry.append("Test Content");

		Assert.assertEquals(UNKNOWN, stackEntry.getState());
	}

	@Test
	public void shouldGetUnknownStateWhenHeaderUnrecognised() throws Exception {
		final JStackEntry stackEntry = new JStackEntry("Thread t@6872:");
		stackEntry.append("Test Content");

		Assert.assertEquals(UNKNOWN, stackEntry.getState());
	}

	@Test
	public void shouldHaveDifferentHash() {
		final JStackEntry stackEntry1 = new JStackEntry("java.lang.Thread.State: BLOCKED (on object monitor)");
		stackEntry1.append("Test Content 1");
		stackEntry1.append("Test Content 2");
		final JStackEntry stackEntry2 = new JStackEntry("java.lang.Thread.State: BLOCKED (on object monitor)");
		stackEntry2.append("Test Content 1");
		stackEntry2.append("Test Content 2");
		stackEntry2.append("Test Content 3");

		Assert.assertNotSame(stackEntry1.hashCode(), stackEntry2.hashCode());
		Assert.assertNotSame(stackEntry1, stackEntry2);
	}

	@Test
	public void shouldHaveDifferentHashStateOnly() {
		final JStackEntry stackEntry1 = new JStackEntry("java.lang.Thread.State: BLOCKED (on object monitor)");
		final JStackEntry stackEntry2 = new JStackEntry("java.lang.Thread.State: RUNNABLE");

		Assert.assertNotSame(stackEntry1.hashCode(), stackEntry2.hashCode());
		Assert.assertNotSame(stackEntry1, stackEntry2);
	}

	@Test
	public void testEmptyContentsAreComparable() {
		final JStackEntry stackEntry1 = new JStackEntry("java.lang.Thread.State: BLOCKED (on object monitor)");
		final JStackEntry stackEntry2 = new JStackEntry("java.lang.Thread.State: BLOCKED (on object monitor)");
		stackEntry2.append("Test Content 1");

		Assert.assertTrue(stackEntry1.compareTo(stackEntry2) < 0);
	}

	@Test
	public void testEqualContentsAreEqualObjects() {
		final JStackEntry stackEntry1 = new JStackEntry("java.lang.Thread.State: BLOCKED (on object monitor)");
		stackEntry1.append("Test Content 1");
		stackEntry1.append("Test Content 2");

		final JStackEntry stackEntry2 = new JStackEntry("java.lang.Thread.State: BLOCKED (on object monitor)");
		stackEntry2.append("Test Content 1");
		stackEntry2.append("Test Content 2");

		Assert.assertEquals(stackEntry1, stackEntry2);
	}

	@Test
	public void testEqualStatesAreComparable() {
		final JStackEntry stackEntry1 = new JStackEntry("java.lang.Thread.State: BLOCKED (on object monitor)");
		final JStackEntry stackEntry2 = new JStackEntry("java.lang.Thread.State: BLOCKED (on object monitor)");

		Assert.assertEquals(0, stackEntry1.compareTo(stackEntry2));
	}

	@Test
	public void testEqualStatesAreEqualObjects() {
		final JStackEntry stackEntry1 = new JStackEntry("java.lang.Thread.State: BLOCKED (on object monitor)");
		final JStackEntry stackEntry2 = new JStackEntry("java.lang.Thread.State: BLOCKED (on object monitor)");

		Assert.assertEquals(stackEntry1, stackEntry2);
	}

	@Test
	public void testShouldGetContentsAsNotNullWhenNullParameter() throws Exception {
		final JStackEntry stackEntry = new JStackEntry("");
		Assert.assertNotNull(stackEntry.getContents());
	}

	@Test
	public void testUnequalContentsAreComparable() {
		final JStackEntry stackEntry1 = new JStackEntry("java.lang.Thread.State: BLOCKED (on object monitor)");
		stackEntry1.append("Test Content 1");
		stackEntry1.append("Test Content 2");

		final JStackEntry stackEntry2 = new JStackEntry("java.lang.Thread.State: BLOCKED (on object monitor)");
		stackEntry2.append("Test Content 1");

		Assert.assertTrue(stackEntry1.compareTo(stackEntry2) > 0);
	}
}
