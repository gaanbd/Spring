package com.camunda.SpringBootCamunda.service.impl;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camunda.SpringBootCamunda.model.Cockpit;
import com.camunda.SpringBootCamunda.service.CockpitService;

@Service
public class CockpitServiceImpl implements CockpitService {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private HistoryService historyService;

	@Override
	public Map<String, Object> getAllProcessDetails() {

		List<ProcessDefinition> processlist = repositoryService.createProcessDefinitionQuery().list();

		long incidentCount = runtimeService.createIncidentQuery().orderByProcessInstanceId().desc().count();

		long humantaskCount = taskService.createTaskQuery().taskAssigned().count();

		long casedefCount = repositoryService.createCaseDefinitionQuery().orderByCaseDefinitionId().desc().count();

		long decisiondefCount = repositoryService.createDecisionDefinitionQuery().orderByDecisionDefinitionId().desc()
				.count();

		List<Cockpit> activeProcessInstance = new ArrayList<Cockpit>();
		List<Cockpit> inactiveProcessInstance = new ArrayList<Cockpit>();
		List<Integer> dataList = new ArrayList<Integer>();

		HashSet<String> processInstanceSet = new HashSet<String>();
		processlist.forEach(process -> {
			processInstanceSet.add(process.getKey());
		});

		processInstanceSet.forEach(process -> {
			if (taskService.createTaskQuery().processDefinitionKey(process).count() > 0) {
				activeProcessInstance.add(new Cockpit(true, process));
			} else {
				inactiveProcessInstance.add(new Cockpit(false, process));
			}

		});

		activeProcessInstance.forEach(instance -> {
			dataList.add(1);
		});

		// getting deployed bpmn files

		File folder = new File("home/sys-user/sofia/Framework/info/bpmn/");
		File[] listOfFiles = folder.listFiles();

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("activeInstance", activeProcessInstance);
		result.put("inactiveInstance", inactiveProcessInstance);
		result.put("activeInstancecount", activeProcessInstance.size());
		result.put("inactiveInstancecount", inactiveProcessInstance.size());
		result.put("data", dataList);
		result.put("incidentCount", incidentCount);
		result.put("humantaskCount", humantaskCount);
		result.put("decisiondefCount", decisiondefCount);
		result.put("casedefCount", casedefCount);
		result.put("processdefCount", activeProcessInstance.size());
		result.put("deploymentFiles", listOfFiles);
		result.put("deployments", listOfFiles.length);
		return result;
	}

	@Override
	public Map<String, Object> getTaskReportData(String startDate, String endDate) throws ParseException {

		// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// Date startDate1 = formatter.parse(startDate);
		// Date startDate2 = formatter.parse(endDate);

		java.sql.Date startdate = java.sql.Date.valueOf(LocalDate.of(2019, 1, 29));
		System.out.println(startdate);

		java.sql.Date enddate = java.sql.Date.valueOf(LocalDate.of(2019, 1, 30));
		System.out.println(enddate);

		List<HistoricTaskInstance> taskReport = historyService.createHistoricTaskInstanceQuery().finishedBefore(enddate)
				.startedAfter(startdate).list();

		Map<String, Object> result = new HashMap<String, Object>();
		List<Cockpit> tasklist = new ArrayList<Cockpit>();

		taskReport.forEach(task -> {

			Cockpit cockbit = new Cockpit();
			cockbit.setStartDate(task.getStartTime());
			cockbit.setEndDate(task.getEndTime());
			cockbit.setAssignee(task.getAssignee());
			cockbit.setDeleteReason(task.getDeleteReason());
			tasklist.add(cockbit);

		});

		result.put("taskReport", tasklist);
		return result;
	}

}
