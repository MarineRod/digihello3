package com.diginamic.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.diginamic.demo.mapper.DepartementMapper;

@Configuration
public class Config {

    @Bean
    DepartementMapper departementMapper() {
		return new DepartementMapper();
	}

}
