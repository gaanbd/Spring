package com.techmango.ehcache.config;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.management.ManagementService;

@Configuration
public class CacheMonitoring {
    @Autowired
    private EhCacheCacheManager ehCacheCacheManager;
    
    @Bean
    public MBeanServer mbeanServer() {
        MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
        return mbeanServer;
    }
    
    @Bean
    public ManagementService managementService() {
        ManagementService managementService = new ManagementService(ehCacheCacheManager.getCacheManager(), mbeanServer(), true, true, true, true);
        managementService.init();
        return managementService;
    }
}
