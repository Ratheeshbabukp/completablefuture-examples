/**
 * 
 */
package com.job.process;

import java.io.Console;
import java.util.concurrent.Executors;

/**
 * @author abc
 *
 */
public class FetchJob extends Thread {

	String message;
	Console console =  System.console();
	
	public void run() {
	  try {
		message = console.readLine();
		System.out.println("message is : "+message);
	  }catch(Exception e) {
		  e.printStackTrace();
	  }
	}
	
}
