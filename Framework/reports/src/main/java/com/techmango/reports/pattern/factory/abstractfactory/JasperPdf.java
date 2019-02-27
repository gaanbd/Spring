package com.techmango.reports.pattern.factory.abstractfactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

public class JasperPdf implements JasperReports{

	@Override
	public byte[] generateJasperReport(String reportType, String reportTitle, Collection<?> datas, String jasperFileName) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("Custom Param", "TVS Electronics");
		parameters.put("dataList", datas);
		try {
			return JasperRunManager.runReportToPdf(jasperFileName, parameters, new JREmptyDataSource());
		} catch (JRException e) {
			e.getMessage();
		}
		return new byte[0];
	}

}
