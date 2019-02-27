package com.techmango.reports.pattern.factory.abstractfactory;

public class FactoryProducer {

	public static AbstractFactory generateReport(boolean isJasper) {
		
		if(isJasper) { 
			return new JasperFactory();
		}
		else {
			return new NonJasperFactory();
		}
	}
}
