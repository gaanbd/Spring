package com.techmango.reports.pattern.factory.abstractfactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class JasperXlsx implements JasperReports{

	@Override
	public byte[] generateJasperReport(String reportType, String reportTitle, Collection<?> datas, String jasperFileName) {
		Map<String, Object> parameters = new HashMap<>();
        parameters.put("Custom Param", "TVS Electronics");
        parameters.put("dataList", datas);
        JasperPrint jasperPrint;
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();)
        {
        	jasperPrint = JasperFillManager.fillReport(jasperFileName, parameters, new JREmptyDataSource());
        	JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
            exporter.exportReport();
            return byteArrayOutputStream.toByteArray();
        }catch(JRException | IOException e) {
    		e.getMessage();
    	}
		return new byte[0];
	}

}
