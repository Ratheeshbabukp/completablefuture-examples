package com.job.main;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import java.util.List;

import java.util.function.Supplier;
import java.util.logging.Logger;

import com.job.pojo.User;
import com.job.service.UserService;

/**
 * Jobs executions using CompletableFuture logic.
 */

public class JobScheduler   {

    Logger log  = Logger.getLogger("JobScheduler.class");

    private UserService userService;

    // Gets all the users .
    private Supplier<List<User>> userSupplier = () -> userService.users();



    /**
     * Get the Job details for Job Listings which are not processed yet.
     */
    public void fetchJobs() {

        log.info("Get the Job Details for each job listings");

        //TODO fetchJob body

        log.info("Job Details execution completed.");
    }

    /**
     * ' Get the job status and update the job.
     */
    public void updateJobStatus() {

        log.info("Job Status Update started.");

        //TODO update job status

        log.info("Job Status Update completed.");
    }


    public static void main(String args[]){

        JobScheduler scheduler = new JobScheduler();
        scheduler.fetchJobs();
        scheduler.updateJobStatus();

    }


}
