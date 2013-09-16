package br.com.atech.commons.jstack.report;

import java.util.Date;
import java.util.Map;

import br.com.atech.commons.jstack.JStackMeta;

/**
 * Provides an interface for compiling a report on a JStackMeta object.
 * 
 * @author Will
 */
public interface Report {
	void buildReport(Map<Date, JStackMeta> map);
}
