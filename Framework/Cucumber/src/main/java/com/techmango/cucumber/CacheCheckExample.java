package com.techmango.cucumber;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

/**
 * Sample component for caching the object in redis cache
 * 
 * @author Techmango
 *
 */
@Component
@EnableCaching
public class CacheCheckExample {
	
	@Cacheable(value="demoCache", condition="'spring'.equals(#language)", key="#language")
	public String getLanguage( final String language) {
		System.out.println("Executing: " + this.getClass().getSimpleName() + ":" + language);
		return "Language:" + language + "!";
	}
	
	
	@Cacheable(value="languageCache", condition="#progLanguage.id==1", key="#progLanguage.id")
	public ProgLanguage processLanguage(ProgLanguage progLanguage) {
		System.out.println("Inside the programming language cache");		
		return progLanguage;
	}



}
