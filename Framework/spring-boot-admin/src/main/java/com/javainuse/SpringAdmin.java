package com.javainuse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.client.ResourceAccessException;

import de.codecentric.boot.admin.config.EnableAdminServer;

@EnableAdminServer
@Configuration
@SpringBootApplication
public class SpringAdmin {

	public static void main(String[] args) {
		try {
			SpringApplication.run(SpringAdmin.class, args);
		}catch(ResourceAccessException accessException) {
			System.out.println("Connection resource exception");
		}
		
	}

	@Configuration
	public static class SecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.formLogin().loginPage("/login.html").loginProcessingUrl("/login").permitAll();
			http.logout().logoutUrl("/logout");
			http.csrf().disable();
			http.authorizeRequests().antMatchers("/login.html", "/**/*.css", "/img/**", "/third-party/**").permitAll();
			http.authorizeRequests().antMatchers("/**").authenticated();
			http.httpBasic();
		}
	}

}
