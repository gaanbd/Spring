package com.techmango.reports.pattern.factory.abstractfactory;

import java.io.ByteArrayOutputStream;
import java.util.Collection;

import org.json.JSONException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.techmango.reports.HeaderFooter;
import com.techmango.reports.PDFCreator;

public class NonJasperPdf implements NonJasperReports{

	@Override
	public byte[] generateNonJasperReport(String reportType, String reportTitle, Collection<?> datas,
			String[] columnNames, String[] fieldNames, String jasperFileName) {
		Document document = null;
		ByteArrayOutputStream bytes = null;
		try {
			document = new Document(PageSize.A4);
			bytes = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, bytes);
			HeaderFooter event = new HeaderFooter();
			//event.setHeader("Header");//add header to the report
			writer.setPageEvent(event);
			document.open();
			PDFCreator.addMetaData(document, reportTitle);
			//PDFCreator.addTitlePage(document, reportTitle);
			PDFCreator.addContent(document, datas, columnNames, fieldNames);
		} catch (DocumentException | JSONException e) {
			e.getMessage();
		} finally {
			if (null != document) {
				document.close();
			}
		}
		return bytes.toByteArray();
	}
}

