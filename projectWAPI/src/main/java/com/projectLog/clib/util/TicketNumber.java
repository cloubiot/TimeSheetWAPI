package com.projectLog.clib.util;

import java.security.SecureRandom;
import java.util.Random;

public class TicketNumber {

	public static void main(String args[]){
		
		
		for(int i=0 ;i<=10;i++){
			SecureRandom random = new SecureRandom();
			int num = random.nextInt(100000);
			String formatted = String.format("%05d", num); 
			String ticket = "TT"+formatted;
			System.out.println(formatted +" ticket "+ticket);
		}
	}
}
