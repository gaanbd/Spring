package com.techmango.ehcache.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableCaching
public class EhcacheConfiguration {

	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactory() {
		EhCacheManagerFactoryBean cacheManagerFactory = new EhCacheManagerFactoryBean();
		cacheManagerFactory.setConfigLocation(new ClassPathResource("ehcache.xml"));
		return cacheManagerFactory;
	}
	
	@Bean
	public EhCacheCacheManager ehCacheCacheManager() {
		EhCacheCacheManager cacheManager = new EhCacheCacheManager();
		cacheManager.setCacheManager(ehCacheManagerFactory().getObject());
		return cacheManager;
	}
}
