package com.brass.jstack.parser;

import com.brass.jstack.JStackMeta;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;

/**
 * @author Will
 */
public class ParserTest {
    @Test
    public void testProcess() throws Exception {
        final URL resource = getClass().getResource("test.jstack");
        final File file = new File(resource.getFile());

        final JStackMeta stackMeta = new Parser(file).process();

        Assert.assertEquals(132, stackMeta.getHeader().length());
        Assert.assertEquals(2, stackMeta.getEntries().size());
    }
}
