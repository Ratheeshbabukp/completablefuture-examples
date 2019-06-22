package com.job.main;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.example.Car;
import com.job.pojo.Message;
import com.job.pojo.User;
import com.job.process.FetchJob;
import com.job.service.UserService;

/**
 * Jobs executions using CompletableFuture logic.
 */

public class JobScheduler   {

    Logger log  = Logger.getLogger("JobScheduler.class");
    private int sampleMessageCount = 10;

    private UserService userService;

    // Gets all the users
    private Supplier<List<User>> userSupplier = () -> userService.users();



    CompletionStage<List<String>>   readMessage() {
    	
        List<String> msg = new ArrayList<String>();
        try {
            for(;sampleMessageCount>0;sampleMessageCount--) msg.add("This is message count "+sampleMessageCount);

            System.out.println("Read messages. Total messages: "+msg.size());

            Thread.sleep(3000);

        }catch(Exception e) {}
    	
    	return CompletableFuture.completedFuture(msg);
    }

    public List<CompletionStage<Message>> processMsg(CompletionStage<List<String>> msgs) {

        List<CompletionStage<Message>> objectMessage = null;
        Message m=null;

        try {

            objectMessage = msgs.toCompletableFuture().get().stream()
                    .map(msg ->  converToObject(msg)).collect(Collectors.toList());

            }catch(Exception e){
                                    e.printStackTrace();
                                }

        return objectMessage;

    }

    /**
       Write down the Tasks which is getting delayed/waited so that need to be Async
     */
    CompletionStage<Message>  converToObject(String msg) {

        return CompletableFuture.supplyAsync(() -> {

            Message message1 = null;

            try {
                    System.out.println("Processing the message : "+msg);
                    try {
                        String uuid = UUID.randomUUID().toString();
                          message1 = new Message(uuid, msg);

                        Thread.sleep(5000);

                        System.out.println("Message Processed : "+msg);
                    }catch(Exception e) {}

            }catch(Exception e) {}

            return message1;
        });
    }


    /**
     *
     * Message notification. Normally a sequential process. need not to be Async
     *
     */
    String notifyMessage(List<CompletionStage<Message>> msg) {
        try {

            //CompletableFuture<List<Message>> msgList = msg.toCompletableFuture();

            msg.forEach(ms -> {
                try {
                    System.out.println("Notifying the message " + ms.toCompletableFuture().get().getContent());
                }catch(Exception e){}
            });



        }catch (Exception e){

        }
    	return "OK";

    }


    /**
     * start the job.
     */
    public void startJob() {

        log.info("Get the Job Details for each job listings");
        
        try {

            CompletableFuture<String> futureTask1 =
                    CompletableFuture.supplyAsync(this::readMessage).thenApplyAsync(this::processMsg).thenApply(this::notifyMessage);

	        		
	        String result = futureTask1.get();
	        Thread.sleep(10000);
	        System.out.println("Get future result(object) : " + result);

        }catch(Exception e) {
        	e.printStackTrace();
        }
        log.info("Job execution completed.");
    }




    public static void main(String args[]){

        JobScheduler scheduler = new JobScheduler();
        scheduler.startJob();

    }


}
