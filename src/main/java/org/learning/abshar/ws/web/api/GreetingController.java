package org.learning.abshar.ws.web.api;

import org.learning.abshar.ws.model.Greeting;
import org.learning.abshar.ws.service.EmailService;
import org.learning.abshar.ws.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Dell on 5/21/2017.
 */
@RestController
public class GreetingController extends BaseController {

    @Autowired
    private GreetingService service;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/api/greetings",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Greeting>> getGreetings() {
        Collection<Greeting> greetings = service.findAll();
        return new ResponseEntity<Collection<Greeting>>(greetings, HttpStatus.OK);

    }

    @RequestMapping(value = "/api/greetings/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Greeting> getGreeting(@PathVariable("id") Long id) {
        Greeting greeting = service.findOne(id);
        if (greeting == null) {
            return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/greetings",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting) {
        Greeting updatedGreeting = service.create(greeting);
        return new ResponseEntity<Greeting>(updatedGreeting, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/greetings",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> updateGreeting(@RequestBody Greeting greeting) {
        Greeting updatedGreeting = service.update(greeting);
        if (updatedGreeting == null) {
            return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Greeting>(updatedGreeting, HttpStatus.OK);

    }

    @RequestMapping(value = "/api/greetings/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Boolean> deleteGreeting(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<Boolean>(true, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "api/greetings/{id}/send",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    //the URL contains optionally a query string parameter named wait defaulted to false
    public ResponseEntity<Greeting> sendGreeting(@PathVariable("id") Long id,
                                                 @RequestParam(value = "wait", defaultValue = "false") boolean waitForAsyncResult) {

        logger.info("> send Greeting");
        Greeting greeting = null;
        try {
            greeting = service.findOne(id);
            if (greeting == null) {
                return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
            }
            if (waitForAsyncResult) {
                Future<Boolean> asyncResponse = emailService.sendAsyncWithResult(greeting);
                boolean emailSent = asyncResponse.get();
                logger.info(" -greeting message sent? {}", emailSent);
            } else {
                //THIS IS SUITABLE TO LONG RUNNING TASKS, THEY CAN BE PERFORMED IN A SEPARATE THREAD
                emailService.sendAsync(greeting);
            }
        } catch (Exception e) {
            logger.error(" -A problem occurred sending the Greeting", e);
            return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info(" < send Greeting");
        return new ResponseEntity<Greeting>(greeting , HttpStatus.OK);
    }
}
