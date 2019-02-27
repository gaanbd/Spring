package com.techmango.reports.pattern.factory.abstractfactory;

public class NonJasperFactory extends AbstractFactory{

	@Override
	public JasperReports getJasperReports(String reportType) {
		return null;
	}

	@Override
	public NonJasperReports getNonJasperReports(String reportType) {
		if(reportType.equalsIgnoreCase("pdf"))
			return new NonJasperPdf();
		else if(reportType.equalsIgnoreCase("xls") || reportType.equalsIgnoreCase("xlsx"))
			return new NonJasperExcel();
		return null;
	}

}
