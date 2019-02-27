package com.techmango.reports.service.impl;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.springframework.http.ResponseEntity;

import com.techmango.reports.model.Test;

import net.sf.jasperreports.engine.JRException;

public interface TestReportsService {
	
	List<Test> getAllEmployees();
	
	ResponseEntity<byte[]> getDataForReports(String reportType, String reportTitle) throws JSONException, IOException;
	
	ResponseEntity<byte[]> getJasperReports(String reportType, String reportTitle) throws JRException;

	ResponseEntity<byte[]> getReports(String reportType, String reportTitle, boolean isJasper) throws JRException;

	ResponseEntity<byte[]> getReportsByAbstractFactoryMethod(String reportType, String reportTitle, boolean isJasper);

}
