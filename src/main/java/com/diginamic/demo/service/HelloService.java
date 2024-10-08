package com.diginamic.demo.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {
	
	 public String getSalutationMessage() {
	        return "Bonjour, bienvenue dans Spring !";
	    }

}
