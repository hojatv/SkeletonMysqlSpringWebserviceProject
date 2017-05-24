package org.learning.abshar.ws.repository;

import org.learning.abshar.ws.model.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Dell on 5/24/2017.
 */
public interface GreetingRepository extends JpaRepository<Greeting,Long>{
}
