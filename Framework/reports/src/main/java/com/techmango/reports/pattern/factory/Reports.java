package com.techmango.reports.pattern.factory;

import java.util.Collection;

public interface Reports {

	public byte[] getReport(String reportType, String reportTitle, Collection<?> datas, String[] columnNames, String[] fieldNames, String jasperFileName);
	
}
