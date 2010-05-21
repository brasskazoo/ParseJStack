package com.brass.jstack.report;

import com.brass.jstack.JStackMeta;

/**
 * Provides an interface for compiling a report on a JStackMeta object.
 * 
 * @author Will
 */
public interface Report {
    void buildReport(JStackMeta stackMeta);
}
