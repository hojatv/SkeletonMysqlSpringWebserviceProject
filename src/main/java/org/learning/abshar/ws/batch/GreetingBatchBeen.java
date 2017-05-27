package org.learning.abshar.ws.batch;

import org.learning.abshar.ws.model.Greeting;
import org.learning.abshar.ws.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Profile("batch")
@Component
public class GreetingBatchBeen {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GreetingService greetingService;

    @Scheduled(cron = "${batch.greeting.cron}")
    public void cronJob() {
        logger.info(" > cronJob");

        //Add scheduled Logic here
        Collection<Greeting> greetings = greetingService.findAll();
        logger.info("There are {} greetings in data store", greetings.size());

        logger.info(" < cronJob");
    }

    @Scheduled(initialDelayString = "${batch.greeting.initialDelay}", fixedRateString = "${batch.greeting.fixedRate}")
    public void fixedRateJobWithInitialDelay() {
        logger.info("> fixedRateJobWithInitialDelay");

        //Add scheduled logic here
        //Simulate job processing time
        long pause = 5000;
        long start = System.currentTimeMillis();
        do {
            if (start + pause < System.currentTimeMillis()) {
                break;
            }
        } while (true);

        logger.info("processing time was {} seconds.", pause / 1000);

        logger.info("< fixedRateJobWithInitialDelay");
    }

    @Scheduled(initialDelayString = "${batch.greeting.initialDelay}", fixedDelayString = "${batch.greeting.fixedDelay}")
    public void fixedDelayJobWithInitialDelay() {
        logger.info("> fixedRateJobWithInitialDelay");

        //Add scheduled logic here
        //Simulate job processing time
        long pause = 5000;
        long start = System.currentTimeMillis();
        do {
            if (start + pause < System.currentTimeMillis()) {
                break;
            }
        } while (true);

        logger.info("processing time was {} seconds.", pause / 1000);

        logger.info("< fixedRateJobWithInitialDelay");
    }
}
