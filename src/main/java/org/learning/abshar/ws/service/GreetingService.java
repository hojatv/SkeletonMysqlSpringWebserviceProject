package org.learning.abshar.ws.service;

import org.learning.abshar.ws.model.Greeting;

import java.util.Collection;

/**
 * Created by Dell on 5/22/2017.
 */
public interface GreetingService {
Collection<Greeting> findAll();
Greeting findOne(Long id);
Greeting create(Greeting greeting);
Greeting update(Greeting greeting);
void delete(Long id);
}
