package org.learning.abshar.ws.service;

import org.learning.abshar.ws.model.Greeting;

import java.util.concurrent.Future;

/**
 * Created by Dell on 5/27/2017.
 */
public interface EmailService {
    Boolean send(Greeting greeting);
    void sendAsync(Greeting greeting);
    Future<Boolean> sendAsyncWithResult(Greeting greeting);
}
