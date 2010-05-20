package com.brass;

import com.brass.jstack.JStackEntry;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author willdampney
 */
public class JStackEntryTest {
    @Test
    public void testShouldGetContentsAsNotNullWhenNullParameter() throws Exception {
        final JStackEntry stackEntry = new JStackEntry(null);
        Assert.assertNotNull(stackEntry.getContents());
    }

    @Test
    public void shouldGetSameContentsAsConstructorParam() throws Exception {
        final StringBuilder contents = new StringBuilder();
        contents.append("Test Content");
        final JStackEntry stackEntry = new JStackEntry(contents);
        Assert.assertEquals(stackEntry.getContents(), contents);
    }
}
