package com.techmango.reports.pattern.factory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;

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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.techmango.reports.HeaderFooter;
import com.techmango.reports.PDFCreator;

public class ReportsPdfExcel implements Reports {

	@Override
	public byte[] getReport(String reportType, String reportTitle, Collection<?> datas, String[] columnNames,
			String[] fieldNames, String jasperFileName) {
		Document document = null;
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		try (Workbook workbook = new HSSFWorkbook();) {
			if (reportType.equals("pdf")) {
				document = new Document(PageSize.A4);
				PdfWriter writer = PdfWriter.getInstance(document, bytes);
				HeaderFooter event = new HeaderFooter();
				event.setHeader("Header");// add header to the report
				writer.setPageEvent(event);
				document.open();
				PDFCreator.addMetaData(document, reportTitle);
				// PDFCreator.addTitlePage(document, reportTitle);
				PDFCreator.addContent(document, datas, columnNames, fieldNames);
			} else if (reportType.equals("xls") || reportType.equals("xlsx")) {
				Sheet sheet = workbook.createSheet();
				Font headerFont = workbook.createFont();
				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 14);
				headerFont.setColor(IndexedColors.RED.getIndex());
				CellStyle headerCellStyle = workbook.createCellStyle();
				headerCellStyle.setFont(headerFont);

				Row headerRow = sheet.createRow(0);
				for (int i = 0; i < columnNames.length; i++) {
					Cell cell = headerRow.createCell(i);
					cell.setCellValue(columnNames[i]);
					cell.setCellStyle(headerCellStyle);
				}

				JsonArray arraysJson = new JsonParser().parse(new Gson().toJson(datas)).getAsJsonArray();
				int rowNum = 1;
				for (Object obj : arraysJson) {
					JSONObject json = new JSONObject(new Gson().toJson(obj));
					Row row = sheet.createRow(rowNum++);
					for (int i = 0; i < fieldNames.length; i++) {
						Cell cell = row.createCell(i);
						cell.setCellValue(String.valueOf(json.get(fieldNames[i])));
						sheet.autoSizeColumn(i);// Resize all columns to fit the content size
					}
				}
				workbook.write(bytes);
			}
		} catch (DocumentException | JSONException | IOException e) {
			e.getMessage();
		} finally {
			if (null != document) {
				document.close();
			}
		}
		return bytes.toByteArray();
	}

}
