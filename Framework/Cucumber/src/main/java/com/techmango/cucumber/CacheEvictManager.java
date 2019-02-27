package com.techmango.cucumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled configuration for evicting the caches
 * 
 * Configured to clear the cache for every 1 hour
 * 
 * @author Techmango
 *
 */
@Component
@EnableScheduling
public class CacheEvictManager {
	
	@Autowired
	private CacheManager cacheManager;
	
	@Scheduled(cron = "0 0 0/1 * * ?")              
    public void clearCacheSchedule(){
        for(String name:cacheManager.getCacheNames()){
        	System.out.println("Cleared Cache:"+name);
            cacheManager.getCache(name).clear();     
        }
    }

}
