package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class Receiver {
	
	static int i = 0;
	
	public void receiveMessage(String message) {
		//System.out.println("Received < " + i++ + ">");
	}
	
	
	
}
