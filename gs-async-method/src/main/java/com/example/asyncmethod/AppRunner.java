package com.example.asyncmethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final GitHubLookupService gitHubLookupService;
    private final GitHubLookupService1 gitHubLookupService1;

    public AppRunner(GitHubLookupService gitHubLookupService, GitHubLookupService1 gitHubLookupService1) {
        this.gitHubLookupService = gitHubLookupService;
        this.gitHubLookupService1 = gitHubLookupService1;
    }

    @Override
    public void run(String... args) throws Exception {
        // Start the clock
        run();
        //run();
    }

    private void run() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        CompletableFuture<User> page1 = gitHubLookupService.findUser("PivotalSoftware");
        CompletableFuture<User> page2 = gitHubLookupService.findUser("CloudFoundry");
        CompletableFuture<User> page3 = gitHubLookupService.findUser("Spring-Projects");

        // Wait until they are all done
        CompletableFuture.allOf(page1, page2, page3).join();

        // Print results, including elapsed time
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start) + "ms");
        logger.info("--> " + page1.get());
        logger.info("--> " + page2.get());
        logger.info("--> " + page3.get());

        start = System.currentTimeMillis();

        page1 = gitHubLookupService1.findUser("PivotalSoftware");
        page2 = gitHubLookupService1.findUser("CloudFoundry");
        page3 = gitHubLookupService1.findUser("Spring-Projects");

        // Wait until they are all done
        CompletableFuture.allOf(page1, page2, page3).join();

        // Print results, including elapsed time
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start) + "ms");
        logger.info("--> " + page1.get());
        logger.info("--> " + page2.get());
        logger.info("--> " + page3.get());
    }

}