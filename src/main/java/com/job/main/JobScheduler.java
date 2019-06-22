package com.job.main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.job.pojo.Message;


/**
 * Jobs executions using CompletableFuture logic.
 */

public class JobScheduler   {

    Logger log  = Logger.getLogger("JobScheduler.class");
    private int sampleMessageCount = 10;


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

            msg.forEach(ms -> {
                try {
                        ms.whenComplete((result,throwable)-> {
                            System.out.println("Notifying the message " + result.getContent());
                            //System.out.println("Errors if any :"+throwable);
                        });
                }catch(Exception e){
                    e.printStackTrace();
                }
            });

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
