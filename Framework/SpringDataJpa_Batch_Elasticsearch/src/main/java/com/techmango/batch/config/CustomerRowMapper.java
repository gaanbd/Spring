
package com.techmango.batch.config;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.techmango.batch.domain.Customer;

public class CustomerRowMapper implements RowMapper<Customer> {
	
	@Override
	public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
		return new Customer(resultSet.getInt("id"), resultSet.getString("firstName"), resultSet.getString("lastName"),
			resultSet.getDate("birthdate"));
	}
}
