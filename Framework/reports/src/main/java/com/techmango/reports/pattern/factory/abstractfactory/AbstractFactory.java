package com.techmango.reports.pattern.factory.abstractfactory;

public abstract class AbstractFactory {

	public abstract JasperReports getJasperReports(String reportType);
	
	public abstract NonJasperReports getNonJasperReports(String reportType);
}
