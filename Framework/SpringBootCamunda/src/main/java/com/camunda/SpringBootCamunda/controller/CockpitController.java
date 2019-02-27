package com.camunda.SpringBootCamunda.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camunda.SpringBootCamunda.service.CockpitService;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/cockpit")
public class CockpitController {

	
	@Autowired
	CockpitService cockpitService;
	
	@RequestMapping(value = "/getAllProcessDetails", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> getAllProcessDetails()
	{	return new ResponseEntity<>(cockpitService.getAllProcessDetails(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getTaskReportData", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> getTaskReportData(@RequestParam String startDate,
			@RequestParam String endDate) throws ParseException
	{	return new ResponseEntity<>(cockpitService.getTaskReportData(startDate, endDate), HttpStatus.OK);
	}
	
}
