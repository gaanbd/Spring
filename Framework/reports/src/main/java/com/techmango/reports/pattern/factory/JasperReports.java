package com.techmango.reports.pattern.factory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class JasperReports implements Reports{

	@Override
	public byte[] getReport(String reportType, String reportTitle, Collection<?> datas, String[] columnNames,
			String[] fieldNames, String jasperFileName) {
		Map<String, Object> parameters = new HashMap<>();
        parameters.put("Custom Param", "TVS Electronics");
        parameters.put("dataList", datas);
        JasperPrint jasperPrint;
        //JasperReport sourceFile = JasperCompileManager.compileReport("E:/siva/my-workspaces/framework/report1.jrxml"); // give the fully qualified path of the .jrxml file
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();)
        {		
		if (!StringUtils.isEmpty(reportType) && reportType.equalsIgnoreCase("pdf")){
			return JasperRunManager.runReportToPdf(jasperFileName, parameters, new JREmptyDataSource());
		}else if(reportType.equals("xls")) {
        	jasperPrint = JasperFillManager.fillReport(jasperFileName, parameters, new JREmptyDataSource());
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
            exporter.exportReport();
            return byteArrayOutputStream.toByteArray();
		}else if(reportType.equals("xlsx")) {
			jasperPrint = JasperFillManager.fillReport(jasperFileName, parameters, new JREmptyDataSource());
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
            exporter.exportReport();
            return byteArrayOutputStream.toByteArray();
		}
	}catch(JRException | IOException e) {
		e.getMessage();
	}
        return new byte[0];
	}
}
