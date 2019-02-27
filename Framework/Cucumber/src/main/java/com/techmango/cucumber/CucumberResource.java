package com.techmango.cucumber;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techmango.common.audit.AuditLog;

@RefreshScope
@RestController
public class CucumberResource {
	
	@Autowired
	CacheCheckExample cacheCheckExample;
	
	@Autowired
	RedisTemplate<Object, Object> redisTemplate;
	
	@Autowired
	RedisCacheManager redisCacheManager;
	
	@RequestMapping(value="/cucumber/getInfo")
	public ResponseEntity<String> getSampleInfo() {
		return new ResponseEntity<>("Hello cucumber!",HttpStatus.OK);
	}

	@RequestMapping(value="/cucumber/postContent",method=RequestMethod.POST)	
	public ResponseEntity<String> postContent(@RequestParam("language") String language) {
		
		// placing the string,string values in cache
		cacheCheckExample.getLanguage(language);
		
		ProgLanguage progLanguage = new ProgLanguage();
		progLanguage.setId(1L);
		progLanguage.setName(language);
		
		ProgLanguage progLanguageUpdated = cacheCheckExample.processLanguage(progLanguage);
		System.out.println("Name after cache:"+progLanguageUpdated.getName());
		
		Cache cache = redisCacheManager.getCache("languageCache");
		ProgLanguage prog = cache.get("1", ProgLanguage.class);
		System.out.println("Name:"+prog.getName());
		
		return new ResponseEntity<>(language,HttpStatus.OK);
	}

	@RequestMapping(value="/cucumber/putContent/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Long> putContent(@PathVariable("id") Long id) {
		return new ResponseEntity<>(id,HttpStatus.OK);
	}
	
	@RequestMapping(value="/cucumber/deleteContent/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteContent(@PathVariable("id") String id) {
		return new ResponseEntity<>(id,HttpStatus.OK);
	}
	
	private AuditLog populateAuditLog(AuditLog auditLog) {
		auditLog.setCreatedBy("Muthu");
		auditLog.setCreatedDate(new Date());
		auditLog.setUpdatedBy("chelvam");
		auditLog.setUpdatedDate(new Date());
		//auditLog.setRequestId(123456L);
		return auditLog;
	}
}