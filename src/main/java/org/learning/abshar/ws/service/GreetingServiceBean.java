package org.learning.abshar.ws.service;

import org.learning.abshar.ws.model.Greeting;
import org.learning.abshar.ws.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class GreetingServiceBean implements GreetingService {


    @Autowired
    GreetingRepository repository;
    @Override
    public Collection<Greeting> findAll() {
        Collection<Greeting> greetings = repository.findAll();
        return greetings;
    }

    @Override
    public Greeting findOne(Long id) {
        Greeting greeting = repository.findOne(id);
        return greeting;
    }

    @Override
    public Greeting create(Greeting greeting) {
        if(greeting.getId()!= null){
            return null;
        }
        Greeting newGreeting = repository.save(greeting);
        return newGreeting;
    }

    @Override
    public Greeting update(Greeting greeting) {
        Greeting greetingPersisted = repository.findOne(greeting.getId());
        if(greetingPersisted == null){
            return null;
        }
        Greeting updatedGreeting = repository.save(greeting);
        return updatedGreeting;
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }
}
