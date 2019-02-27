package com.techmango.reports;

import java.io.IOException;
import java.util.Collection;

import org.json.JSONException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import net.sf.jasperreports.engine.JRException;


public interface ReportsService {

	//Generic methods
	ResponseEntity<byte[]> generateJasperReports(String reportType, String reportTitle, String jasperFileName,
			Collection<?> datas) throws JRException;

	HttpHeaders prepareTemplateHeader(String fileType, String fileName);

	byte[] generateReportsPdf(String reportType, String reportTitle, Collection<?> resultSet, String[] columnNames, String[] fieldNames)
			throws JSONException;
	
	byte[] generateReportsExcel(String reportType, String reportTitle, Collection<?> resultSet, String[] columnNames,
			String[] fieldNames) throws JSONException, IOException;

}
