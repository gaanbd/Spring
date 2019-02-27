package com.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.model.Chart;
import com.repo.TicketRepository;

@Controller
public class WebController {

	@Autowired
	TicketRepository ticketRepository;
	
	@MessageMapping("/livechart")
	@SendTo("/topic/piechart")
	public ArrayList<Chart> updateChart() throws Exception {
	
		Chart chart1 = new Chart();
		int newTicketsCount = ticketRepository.findByTicketStatus(1);
		chart1.setX("New");
		chart1.setY(newTicketsCount);
		
		Chart chart2 = new Chart();
		int inpTicketsCount = ticketRepository.findByTicketStatus(2);
		chart2.setX("InProgress");
		chart2.setY(inpTicketsCount);
		
		Chart chart3 = new Chart();
		int closedTicketsCount = ticketRepository.findByTicketStatus(3);
		chart3.setX("Closed");
		chart3.setY(closedTicketsCount);
		
		ArrayList<Chart> chartdata = new ArrayList<Chart>();
		chartdata.add(chart1);
		chartdata.add(chart2);
		chartdata.add(chart3);
		
		return chartdata;
		
		
	}
}
