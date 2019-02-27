package com.techmango.reports;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Service
public class ReportsServiceImpl implements ReportsService {

	@PersistenceContext
	private EntityManager entityManager;
	

	//Generic Method for PDF
	@Override
	public byte[] generateReportsPdf(String reportType, String reportTitle, Collection<?> resultSet, String[] columnNames, String[] fieldNames) throws JSONException {
		Document document = null;
		ByteArrayOutputStream bytes = null;
		try {
			document = new Document(PageSize.A4);
			bytes = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, bytes);
			HeaderFooter event = new HeaderFooter();
			//event.setHeader("Header");
			writer.setPageEvent(event);
			document.open();
			PDFCreator.addMetaData(document, reportTitle);
			PDFCreator.addTitlePage(document, reportTitle);
			PDFCreator.addContent(document, resultSet, columnNames, fieldNames);
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			if (null != document) {
				document.close();
			}
		}
		return bytes.toByteArray();
	}
	
	//Generic Method for Excel
	@Override
	public byte[] generateReportsExcel(String reportType, String reportTitle, Collection<?> resultSet, String[] columnNames, String[] fieldNames) throws JSONException, IOException {
		Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);
        for(int i = 0; i < columnNames.length; i++) {
            Cell cell = headerRow.createCell(i);
       		cell.setCellValue(columnNames[i]);
            cell.setCellStyle(headerCellStyle);
        }

        JsonArray arraysJson = new JsonParser().parse(new Gson().toJson(resultSet)).getAsJsonArray();
        int rowNum = 1;
        for(Object obj: arraysJson) {
        	JSONObject json = new JSONObject(new Gson().toJson(obj));
            Row row = sheet.createRow(rowNum++);
            for(int i = 0; i < fieldNames.length; i++) {
                Cell cell = row.createCell(i);
               	cell.setCellValue(String.valueOf(json.get(fieldNames[i])));
                sheet.autoSizeColumn(i);//Resize all columns to fit the content size
            }
         }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
	        workbook.write(bos);
		} catch (IOException io) {
			io.getMessage();
		}
		finally {
			bos.close();
			workbook.close();
		}
		return bos.toByteArray();
	}
	
	//Generic method
	@Override
	public HttpHeaders prepareTemplateHeader(String fileType,String fileName) {
		HttpHeaders headers = new HttpHeaders();
		if (!StringUtils.isEmpty(fileType) && fileType.equalsIgnoreCase("pdf")){
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
		}else if(fileType.equals("xls")) {
			headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
		}else if(fileType.equals("xlsx")) {
			headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		}
		headers.set("fileName", fileName);
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		return headers;
	}

	@Override
	public ResponseEntity<byte[]> generateJasperReports(String reportType, String reportTitle, String jasperFileName, Collection<?> datas) throws JRException {
		Map<String, Object> parameters = new HashMap<>();
        parameters.put("Custom Param", "TVS Electronics");
        parameters.put("dataList", datas);
        byte[] byteArray = null ;
        JasperPrint jasperPrint;
        //JasperReport sourceFile = JasperCompileManager.compileReport("E:/siva/my-workspaces/framework/report1.jrxml"); // give the fully qualified path of the .jrxml file
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        
		if (!StringUtils.isEmpty(reportType) && reportType.equalsIgnoreCase("pdf")){
			byteArray = JasperRunManager.runReportToPdf(jasperFileName, parameters, new JREmptyDataSource());

		}else if(reportType.equals("xls")) {
        	jasperPrint = JasperFillManager.fillReport(jasperFileName, parameters, new JREmptyDataSource());
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
            exporter.exportReport();
            byteArray = byteArrayOutputStream.toByteArray();
		}else if(reportType.equals("xlsx")) {
			jasperPrint = JasperFillManager.fillReport(jasperFileName, parameters, new JREmptyDataSource());
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
            exporter.exportReport();
            byteArray = byteArrayOutputStream.toByteArray();
		}
		HttpHeaders headers = prepareTemplateHeader(reportType, reportTitle);
		return new ResponseEntity<>(byteArray, headers, HttpStatus.OK);
	}

	

}
