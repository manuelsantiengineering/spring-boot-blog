package com.mesanti.blog.config;

import java.net.InetAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.transport.TransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.TransportClientFactoryBean;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.mesanti.blog.repositories")
@ComponentScan(basePackages = { "com.mesanti.blog.config" })
public class ElasticDataConfig {

	 	@Value("${elasticsearch.host}")
	    private String esHost;

	    @Value("${elasticsearch.port}")
	    private int esPort;

	    @Value("${elasticsearch.clustername}")
	    private String esClusterName;

	    @Bean
	    public Client client() throws Exception {

	        /*Settings esSettings = Settings.builder()
	                .put("cluster.name", esClusterName)
	                .build();*/
	        //https://www.elastic.co/guide/en/elasticsearch/guide/current/_transport_client_versus_node_client.html
	        TransportClientFactoryBean transportClientFactory = new TransportClientFactoryBean();
	        transportClientFactory.setClusterName(esClusterName);
	        transportClientFactory.afterPropertiesSet();
	        
	        return transportClientFactory.getObject()
	                  .addTransportAddress(
					  new TransportAddress(InetAddress.getByName(esHost), esPort));
	    }

	    @Bean
	    public ElasticsearchTemplate elasticsearchTemplate() throws Exception {
	    	Client client = client();
	    	ElasticsearchTemplate esTemplate = new ElasticsearchTemplate(client);
	    	System.out.println("\n\n\n\n\n Done\n\n\n\n\n");
	        return esTemplate;
	    }

}







//public class ElasticDataConfig {
//
//	 	@Value("${elasticsearch.host}")
//	    private String esHost;
//
//	    @Value("${elasticsearch.port}")
//	    private int esPort;
//
//	    @Value("${elasticsearch.clustername}")
//	    private String esClusterName;
//
//	    @Bean
//	    public Client client() throws Exception {
//	    	
//	    	Settings settings = Settings.builder()
//	    	        .put("cluster.name", esClusterName)
//	    	        .put("client.transport.sniff", true).build();
//	    	System.out.println("\n\n\n\n" + "6 - ElasticSearch Host: " + esHost + " Port: "+ esPort + "\n\n\n\n");
//	    	TransportClient transportClient = new PreBuiltTransportClient(settings);
////	    	transportClient.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300))
//	    	transportClient.addTransportAddress(new TransportAddress(InetAddress.getByName(esHost), esPort));
////	    	transportClient.addTransportAddress(new TransportAddress(InetAddress.getByName(esHost), 9201));
//
//	    	System.out.println("\n\n\n\n" + "7 - ElasticSearch Host: " + esHost + " Port: "+ esPort + "\n\n\n\n" + transportClient.connectedNodes().toString());
//	    	
//	    	return transportClient;
//	    }
//
//	    @Bean
//	    public ElasticsearchTemplate elasticsearchTemplate() throws Exception {
//	    	System.out.println("\n\n\n\n" + "1 - ElasticSearch Host: " + esHost + " Port: "+ esPort + "\n\n\n\n");
//	    	Client client = client();
//	    	System.out.println("\n\n\n\n" + "10 - ElasticSearch Host: " + esHost + " Port: "+ client.toString() + "\n\n\n\n" + client.toString() + "\n\n\n\n");
//	        return new ElasticsearchTemplate(client);
//	    }
//
//}