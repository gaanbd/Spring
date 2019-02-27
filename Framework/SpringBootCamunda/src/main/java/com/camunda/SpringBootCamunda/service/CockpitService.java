package com.camunda.SpringBootCamunda.service;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

public interface CockpitService {

	Map<String,Object> getAllProcessDetails();

	Map<String,Object> getTaskReportData(String startDate, String endDate) throws ParseException;
}
