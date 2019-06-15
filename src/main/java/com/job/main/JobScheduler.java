package com.job.main;

import static java.util.concurrent.CompletableFuture.supplyAsync;

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

    Logger log  = Logger.getLogger("JobScheduler");

    private UserService userService;

    // Gets all the users .
    private Supplier<List<User>> userSupplier = () -> userService.users();

    CompletionStage<String>   findReceiver() {
    	return CompletableFuture.completedFuture("myMessage");
    }
    CompletionStage<String>  sendMsg(CompletionStage<String> msg) {
    	String msgss= msg.toString();
    	return CompletableFuture.completedFuture(msgss);
    }
    CompletionStage<String> returnvalue() {
    	//String msgss= msg.toString();
    	//return   CompletableFuture.thenCompose(  sd ->{
    		return CompletableFuture.completedFuture("sd");
    	//});
    	//return "ss";
    }
    void notify(CompletionStage<String> msg) {
    	
    }

    /**
     * Get the Job details for Job Listings which are not processed yet.
     */
    public void fetchJobs() {

        log.info("Get the Job Details for each job listings");
        ExecutorService executor = Executors.newFixedThreadPool(7);
        // Callable, return a future, submit and run the task async
        Runnable fetch = new FetchJob();
        
        CompletableFuture<String> futureTask1 = 
        		CompletableFuture.supplyAsync(this::findReceiver).thenApplyAsync((s) -> s+" : is x+1");
        
        System.out.println("futureTask1 "+futureTask1);
        
       // CompletableFuture<String> futureTask2 = 
        	//	CompletableFuture.supplyAsync(this::findReceiver).thenApply(this::sendMsg).thenCompose(cd -> this::returnValue);
        		
        		/*CompletableFuture.runAsync(fetch, executor).thenConsumeAsync(s-> {
        int k=10;
        System.out.println("In thenAsync fn...");
        return 18;
        });  */

        try {
        String result = futureTask1.get();//2, TimeUnit.SECONDS);
        Thread.sleep(10000);
        System.out.println("Get future result : " + result);
        }catch(Exception e) {}
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
