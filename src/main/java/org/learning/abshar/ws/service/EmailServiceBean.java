package org.learning.abshar.ws.service;

import org.learning.abshar.ws.model.Greeting;
import org.learning.abshar.ws.util.AsyncResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class EmailServiceBean implements EmailService{

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public Boolean send(Greeting greeting) {
        logger.info("> Send");
        Boolean success = Boolean.FALSE;
        //Simulate method execution time
        long pause = 5000;
        try {
            Thread.sleep(pause);
        } catch (InterruptedException e) {

        }
        logger.info("processing time was {} seconds" , pause/1000);
        success = Boolean.TRUE;
        return success;
    }

    //The component scanner identifies this method as async and when invoked uses a task manager to rub the method in a new thread
    //This method does not return response so the initiating thread continues to run in parallel with the email service
    //SEND AND FORGET
    @Async
    @Override
    public void sendAsync(Greeting greeting) {
        logger.info(" > sendAsync");

        try{
            send(greeting);
        }catch (Exception e){
            logger.warn("Exception caught during sending async email. " , e);
        }
        logger.info(" < sendAsync");

    }

    @Async
    @Override
    public Future<Boolean> sendAsyncWithResult(Greeting greeting) {
        logger.info(" > sendAsyncWithResult");

        AsyncResponse<Boolean> response = new AsyncResponse<Boolean>();
        try {
            Boolean success = send(greeting);
            response.complete(success);
        }catch (Exception e){
            logger.warn("Exception caught during sending aync email. " , e);
            response.isCompletedExceptionally();
        }

        logger.info(" < sendAsyncWithResult");
        return response;


    }
}
