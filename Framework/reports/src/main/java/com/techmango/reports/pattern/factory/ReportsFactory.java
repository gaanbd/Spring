package com.techmango.reports.pattern.factory;

public class ReportsFactory {

	public Reports getReports(boolean isJasper) {
		if(isJasper) {
			return new JasperReports();
		}else {
			return new ReportsPdfExcel();
		}
	}
}
