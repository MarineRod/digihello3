package com.diginamic.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config2 {
	
	 @Bean
	    public RestTemplate restTemplate() {
	        return new RestTemplate();
	    }

}
