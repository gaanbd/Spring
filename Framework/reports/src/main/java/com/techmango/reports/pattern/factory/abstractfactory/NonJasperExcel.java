package com.techmango.reports.pattern.factory.abstractfactory;

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

public class NonJasperExcel implements NonJasperReports{

	@Override
	public byte[] generateNonJasperReport(String reportType, String reportTitle, Collection<?> datas,
			String[] columnNames, String[] fieldNames, String jasperFileName) {
		Document document = null;
		try (ByteArrayOutputStream bytes = new ByteArrayOutputStream(); Workbook workbook = new HSSFWorkbook();) {
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
			return bytes.toByteArray();
		} catch (JSONException | IOException e) {
			e.getMessage();
		} finally {
			if (null != document) {
				document.close();
			}
		}
		return new byte[0];
	}

}
