package com.techmango.reports.controller;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techmango.reports.model.Test;
import com.techmango.reports.service.impl.TestReportsService;

import net.sf.jasperreports.engine.JRException;

@RestController
public class TestReportsController {

	@Autowired(required=true)
	TestReportsService testReportsService;
	
	@GetMapping
	@RequestMapping(value = "/getAll")
	public ResponseEntity<List<Test>> getAll() {
		return new ResponseEntity<>(testReportsService.getAllEmployees(), HttpStatus.OK);
	}
	
	@GetMapping
	@RequestMapping(value = "/export/reports")
	public ResponseEntity<byte[]> getReports(@RequestParam("reportType") String reportType, @RequestParam("reportTitle") String reportTitle) throws JSONException, IOException {
		return testReportsService.getDataForReports(reportType, reportTitle);
	}
	
	@GetMapping
	@RequestMapping(value = "/export/jasperReports")
	public ResponseEntity<byte[]> getJasperReports(@RequestParam("reportType") String reportType, @RequestParam("reportTitle") String reportTitle) throws JSONException, IOException, JRException {
		return testReportsService.getJasperReports(reportType, reportTitle);
	}

}
