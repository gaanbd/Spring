package com.techmango.es.config;

import java.net.InetAddress;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.techmango.batch.repository")
public class ESconfig {

	@Value("${elasticsearch.cluster.name:tmframe}")
    private String clusterName;
	
	@Bean
	public TransportClient client() throws Exception {

		Settings elasticsearchSettings = Settings.builder()
		          .put("client.transport.sniff", true)
		          .put("cluster.name", clusterName).build();
		        TransportClient client = new PreBuiltTransportClient(elasticsearchSettings)
		//TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
		        .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
		return client;

	}

	/*@Bean
	public ElasticsearchOperations elasticsearchTemplate() throws Exception {
		return new ElasticsearchTemplate(client());
	}*/

}