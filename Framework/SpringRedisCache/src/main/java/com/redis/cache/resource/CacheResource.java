package com.redis.cache.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CacheResource {
	
	@Autowired
	RedisCacheManager redisCacheManager;

	@RequestMapping(value="/cache/getValue")
	public ResponseEntity<String> getSampleInfo() {
		Cache cache = redisCacheManager.getCache("demoCache");
		String value = cache.get("spring", String.class);
		System.out.println("Cached value from other microservice:"+value);
		return new ResponseEntity<>("Hello cucumber!",HttpStatus.OK);
	}

}
