package com.techmango.reports.pattern.factory.abstractfactory;

import java.util.Collection;

public interface JasperReports {

	public byte[] generateJasperReport(String reportType, String reportTitle, Collection<?> datas, String jasperFileName);
}
