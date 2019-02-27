package com.techmango.reports.pattern.factory.abstractfactory;

public class JasperFactory extends AbstractFactory{

	@Override
	public JasperReports getJasperReports(String reportType) {
		if(reportType.equalsIgnoreCase("pdf"))
			return new JasperPdf();
		else if(reportType.equalsIgnoreCase("xls"))
			return new JasperXls();
		else if(reportType.equalsIgnoreCase("xlsx"))
			return new JasperXlsx();
		return null;
	}

	@Override
	public NonJasperReports getNonJasperReports(String reportType) {
		return null;
	}

}
