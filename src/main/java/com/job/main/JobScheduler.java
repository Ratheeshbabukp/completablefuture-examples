package com.job.main;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Logger;

import com.job.pojo.User;
import com.job.process.FetchJob;
import com.job.service.UserService;

/**
 * Jobs executions using CompletableFuture logic.
 */

public class JobScheduler   {

    Logger log  = Logger.getLogger("JobScheduler.class");

    private UserService userService;

    // Gets all the users .
    private Supplier<List<User>> userSupplier = () -> userService.users();

    CompletionStage<List<String>>   findReceiver() {
    	
        List<String> msg = new ArrayList<String>();
        try {
 		  
 		
        msg.add("1 India is my country.");
        System.out.println("Received messages ...1");
        Thread.sleep(1000);
        
        msg.add("2 Pak is not my country.");
        System.out.println("Received messages ...2");
        Thread.sleep(1000);
        
        msg.add("3 Englad is Cricket  country.");
        System.out.println("Received messages ...3");
        Thread.sleep(1000); 
        
        msg.add("4 Brazil is football  country.");
        System.out.println("Received messages ...4");
        Thread.sleep(1000);
        
        msg.add("5 Afgan is terror country.");
        System.out.println("Received messages ...5");
        Thread.sleep(1000);
        
        msg.add("6 America is developed country.");
        System.out.println("Received messages ...6");
        
        }catch(Exception e) {}
    	
    	return CompletableFuture.completedFuture(msg);
    }
    
    CompletionStage<List<String>>  sendMsg(CompletionStage<List<String>> msg) {
    	List<String> msgss=null;
    	try {
    	// msgss = CompletableFuture.completedFuture(msg.to);//.toCompletableFuture().get());
    	 msgss.forEach(msg1 -> {
    		 System.out.println("Sending the message : "+msg1);
    		 try {
    		   Thread.sleep(1000);
    		 }catch(Exception e) {}
    	 });
    	
    	}catch(Exception e) {}
    	return CompletableFuture.completedFuture(msgss);
    }
     
    CompletionStage<List<String>> notify(CompletionStage<List<String>> msg) {
    	System.out.println("Notifying the message "+msg);
    	return msg;
    }

    /**
     * Get the Job details for Job Listings which are not processed yet.
     */
    public void fetchJobs() {

        log.info("Get the Job Details for each job listings");
        
        try {
	        CompletableFuture<Object> futureTask1 = 
	        		CompletableFuture.supplyAsync(this::findReceiver).thenApplyAsync(this::sendMsg).thenApply(this::notify);
	        		
	        Object result = futureTask1.get();//2, TimeUnit.SECONDS);
	        Thread.sleep(10000);
	        System.out.println("Get future result(object) : " + result);
        }catch(Exception e) {
        	e.printStackTrace();
        }
        log.info("Job Details execution completed.");
    }

    /**
     *  Get the job status and update the job.
     */
    public void updateJobStatus() {

        log.info("Job Status Update started.");

        //TODO update job status

        log.info("Job Status Update completed.");
    }


    public static void main(String args[]){

        JobScheduler scheduler = new JobScheduler();
        scheduler.fetchJobs();
        //scheduler.updateJobStatus();

    }


}
