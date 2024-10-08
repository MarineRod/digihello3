package com.diginamic.demo.test;

public class Singleton {
	
	private final static Singleton INSTANCE = new Singleton();
	
	private Singleton() {
		
	}

	public static Singleton getInstance() {
		return INSTANCE;
		
	}
}
