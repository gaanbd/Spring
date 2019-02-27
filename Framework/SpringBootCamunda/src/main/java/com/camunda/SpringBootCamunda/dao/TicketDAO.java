package com.camunda.SpringBootCamunda.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.camunda.SpringBootCamunda.model.Ticket;


@Repository
public interface TicketDAO extends CrudRepository<Ticket, Long> {

	List<Ticket> findByUsername(String username); 
	
	List<Ticket> findByCustomeroperatorname(String cusoptname); 
	
	List<Ticket> findByEngineername(String engname); 
	
	List<Ticket> findByServicecentername(String sercenname); 
	
	Ticket findById(long id);
	
	Ticket findByInstanceId(String instanceid);
	
	@Query("select ticket from Ticket ticket where ticket.customeroperatorname = :cusoptname and ticket.ticketStatus != 'closed'")
	List<Ticket> findCustomeroperatorOpenTasks(@Param("cusoptname") String cusoptname); 
	
	@Query("select ticket from Ticket ticket where ticket.engineername = :engname and ticket.engineerWorkStatus = false and ticket.ticketStatus != 'closed'")
	List<Ticket> findEngineerOpenTasks(@Param("engname") String engname); 
	
	@Query("select ticket from Ticket ticket where ticket.servicecentername = :sercenname and ticket.servicecenterstatus = false and ticket.ticketStatus != 'closed'")
	List<Ticket> findServicecenterOpenTasks(@Param("sercenname") String sercenname); 
}
