package com.springbootbatch.batchsteps;

import org.springframework.batch.item.ItemProcessor;

import com.springbootbatch.model.User;

public class CustomItemProcessor implements ItemProcessor<User, User> {

	 public User process(User user) throws Exception {		 
		 System.out.println("emp_"+user.getId());
		 user.setEmpCode("emp_"+user.getId());
		 return user;
	 }
}
