package com.camunda.SpringBootCamunda.dmn;

import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.application.impl.ServletProcessApplication;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;

public class DMNApplication extends ServletProcessApplication {

	  @PostDeploy
	  public void evaluateDecisionTable(ProcessEngine processEngine) {

	    DecisionService decisionService = processEngine.getDecisionService();

	    VariableMap variables = Variables.createVariables()
	      .putValue("season", "Spring")
	      .putValue("guestCount", 10);

	    DmnDecisionTableResult dishDecisionResult = 
	    		decisionService.evaluateDecisionTableByKey("dish", variables);
	    String desiredDish = dishDecisionResult.getSingleEntry();

	    System.out.println("Desired dish: " + desiredDish);
	  }

}
