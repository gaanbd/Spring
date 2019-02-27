package com.techmango.reports.pattern.factory.abstractfactory;

import java.util.Collection;

public interface NonJasperReports {

	public byte[] generateNonJasperReport(String reportType, String reportTitle, Collection<?> datas, String[] columnNames,
			String[] fieldNames, String jasperFileName);
}
