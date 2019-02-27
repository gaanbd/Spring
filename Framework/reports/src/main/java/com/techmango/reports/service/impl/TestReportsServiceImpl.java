package com.techmango.reports.service.impl;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.techmango.reports.ReportsService;
import com.techmango.reports.model.Test;
import com.techmango.reports.pattern.factory.Reports;
import com.techmango.reports.pattern.factory.ReportsFactory;
import com.techmango.reports.pattern.factory.abstractfactory.AbstractFactory;
import com.techmango.reports.pattern.factory.abstractfactory.FactoryProducer;
import com.techmango.reports.repository.TestRepository;

import net.sf.jasperreports.engine.JRException;

@Service
public class TestReportsServiceImpl implements TestReportsService{

	@Autowired
	private TestRepository testRepository;
	
	@Autowired
	private ReportsService reportsService;

	@Override
	public List<Test> getAllEmployees() {
		return (List<Test>) testRepository.findAll();
	}
	
	@Override
	public ResponseEntity<byte[]> getDataForReports(String reportType, String reportTitle) throws JSONException, IOException {
		byte[] byteArray = null;
		HttpHeaders headers = null;
		String[] columnNames = {"Id","Name","Age","Salary"};
		String[] fieldNames = {"id","name","age","salary"};
		if(reportType.equals("pdf")) {
			byteArray = reportsService.generateReportsPdf(reportType, reportTitle, getAllEmployees(), columnNames, fieldNames);
			headers = reportsService.prepareTemplateHeader(reportType, reportTitle);
		}else if (reportType.equals("xls") || reportType.equals("xlsx")) {
			byteArray = reportsService.generateReportsExcel(reportType, reportTitle, getAllEmployees(), columnNames, fieldNames);
			headers = reportsService.prepareTemplateHeader(reportType, reportTitle);
		}
		return new ResponseEntity<>(byteArray, headers, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<byte[]> getJasperReports(String reportType, String reportTitle) throws JRException {
		return reportsService.generateJasperReports(reportType, reportTitle, "E:/siva/my-workspaces/framework/report1.jasper", getAllEmployees());
	}

	@Override
	public ResponseEntity<byte[]> getReports(String reportType, String reportTitle, boolean isJasper) throws JRException {
		String[] columnNames = {"Id","Name","Age","Salary"};
		String[] fieldNames = {"id","name","age","salary"};
		Reports factory = new ReportsFactory().getReports(isJasper);
		return new ResponseEntity<>(factory.getReport(reportType, reportTitle, getAllEmployees(), columnNames, fieldNames, "E:/siva/my-workspaces/framework/report1.jasper"), reportsService.prepareTemplateHeader(reportType, reportTitle), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<byte[]> getReportsByAbstractFactoryMethod(String reportType, String reportTitle,
			boolean isJasper) {
		String[] columnNames = {"Id","Name","Age","Salary"};
		String[] fieldNames = {"id","name","age","salary"};
		AbstractFactory absfactory = FactoryProducer.generateReport(isJasper);
		List<Test> datas = getAllEmployees();
		byte[] byteArray = null;
		if(isJasper) {
			byteArray = absfactory.getJasperReports(reportType).generateJasperReport(reportType, reportTitle, datas, "E:/siva/my-workspaces/framework/report1.jasper");
			
		}else {
			byteArray = absfactory.getNonJasperReports(reportType).generateNonJasperReport(reportType, reportTitle, getAllEmployees(), columnNames, fieldNames, null);
		}
		
		return new ResponseEntity<>(byteArray, reportsService.prepareTemplateHeader(reportType, reportTitle), HttpStatus.OK);
	}
	
}
