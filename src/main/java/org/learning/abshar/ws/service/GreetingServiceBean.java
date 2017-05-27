package org.learning.abshar.ws.service;

import org.learning.abshar.ws.model.Greeting;
import org.learning.abshar.ws.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.SUPPORTS,
        readOnly = true)
public class GreetingServiceBean implements GreetingService {


    @Autowired
    GreetingRepository repository;

    @Override
    public Collection<Greeting> findAll() {
        Collection<Greeting> greetings = repository.findAll();
        return greetings;
    }

    @Override
    @Cacheable(value = "greetings", key = "#id")
    public Greeting findOne(Long id) {
        Greeting greeting = repository.findOne(id);
        return greeting;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CachePut(value = "greetings", key = "#result.id")
    public Greeting create(Greeting greeting) {
        if (greeting.getId() != null) {
            return null;
        }
        Greeting newGreeting = repository.save(greeting);
        if (newGreeting.getId() == 4L) {
            throw new RuntimeException("Roll me back!");
        }
        return newGreeting;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CachePut(value = "greetings", key = "#greeting.id")
    public Greeting update(Greeting greeting) {
        Greeting greetingPersisted = repository.findOne(greeting.getId());
        if (greetingPersisted == null) {
            return null;
        }
        Greeting updatedGreeting = repository.save(greeting);
        return updatedGreeting;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CacheEvict(value = "greetings", key = "#id")
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    @CacheEvict(value = "greetings" , allEntries = true)
    public void evictCache(){

    }
}
