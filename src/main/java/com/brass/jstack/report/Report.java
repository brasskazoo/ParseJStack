package com.brass.jstack.report;

import com.brass.jstack.JStackMeta;

/**
 * @author willdampney
 */
public interface Report {
    void buildReport(JStackMeta stackMeta);
}
