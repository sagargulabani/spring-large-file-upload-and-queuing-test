package com.example.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Runner implements CommandLineRunner{
	
	@Autowired
	DataRepository dataRepository;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	
	@Override
	public void run(String... arg0) throws Exception {
		
		
		ObjectMapper mapper = new ObjectMapper();
		
		InputStream jsonDataStream = ClassLoader.getSystemResourceAsStream("batchdata.json");
		
		List<Data> list = mapper.readValue(jsonDataStream, new TypeReference<List<Data>>(){});
		
		System.out.println("startTime");
		
//		long startTime = System.currentTimeMillis();
//		System.out.println(startTime);
//		
//		list.parallelStream().forEach(data -> {
//			dataRepository.save(data);
//		});
//		
//		long finishTime = System.currentTimeMillis();
//		System.out.println("finish time");
//		System.out.println(finishTime);
//		System.out.println("Difference");
//		System.out.println(finishTime-startTime);
//		
//		System.out.println(list.size()); 
//		System.out.println(list.get(0).name);
//		
//		long readDataStartTime = System.currentTimeMillis();
//		System.out.println("readDataStartTime");
//		System.out.println(readDataStartTime);
//		
//		list = null;
//		
//		List<Data> list2 = dataRepository.findAll();
//		
//		long readDataFinishTime = System.currentTimeMillis();
//		
//		System.out.println("readDataFinishTime");
//		System.out.println(readDataFinishTime);
//		
//		System.out.println("difference");
//		System.out.println(readDataFinishTime - readDataStartTime);
//		
//		
//		System.out.println(list2.size());
		
		
		long queueingStartTime = System.currentTimeMillis();
		System.out.println("queueingStartTime");
		System.out.println(queueingStartTime);
		
		list.parallelStream().forEach(item -> {
			
			for(int i = 0; i < 50; i++ ) {
				int a = i*i;
				int b = i*i*i;
			}
			rabbitTemplate.convertAndSend("spring-boot","hello");
			
		});
		
		long queueingFinishTime = System.currentTimeMillis();
		System.out.println("queueingFinishTime");
		System.out.println(queueingFinishTime);
		
		
		System.out.println("difference");
		System.out.println(queueingFinishTime - queueingStartTime);
		
		
		String jsonRequest = mapper.writeValueAsString(list);
		
		RestTemplate template = new RestTemplate();
		
		
		
		
		try {
			
			template.getMessageConverters().add(new StringHttpMessageConverter());
			template.getMessageConverters().add(new FormHttpMessageConverter());
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("file", new ClassPathResource("batchData.json"));
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(map, headers);
			
	        
			String response = template.postForObject("http://localhost:8080/upload", httpEntity, String.class);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//Thread.sleep(1000000);
		
		
		
		
		
	}
	
	
	
}
