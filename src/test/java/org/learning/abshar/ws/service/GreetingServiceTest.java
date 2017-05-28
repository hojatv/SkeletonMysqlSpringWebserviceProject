package org.learning.abshar.ws.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.learning.abshar.ws.AbstractTest;
import org.learning.abshar.ws.model.Greeting;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.Collection;

@Transactional
public class GreetingServiceTest extends AbstractTest {
    @Autowired
    GreetingService service;


    @Before
    public void setup() {
        //This method is called before running each method in the class
        service.evictCache();
    }

    @After
    public void tearDown() {
        //This method is called after running each method in the class

    }

    @Test
    public void testFindAll() {
        Collection<Greeting> greetings = service.findAll();
        Assert.assertNotNull("-failure - expected not Null", greetings);
    }

    @Test
    public void testFindOne() {

        Long id = new Long(1);

        Greeting entity = service.findOne(id);

        Assert.assertNotNull("failure - expected not null", entity);
        Assert.assertEquals("failure - expected id attribute match", id,
                entity.getId());

    }

    @Test
    public void testFindOneNotFound() {

        Long id = Long.MAX_VALUE;

        Greeting entity = service.findOne(id);

        Assert.assertNull("failure - expected null", entity);

    }

    @Test
    public void testCreate() {

        Greeting entity = new Greeting();
        entity.setText("test");

        Greeting createdEntity = service.create(entity);

        Assert.assertNotNull("failure - expected not null", createdEntity);
        Assert.assertNotNull("failure - expected id attribute not null",
                createdEntity.getId());
        Assert.assertEquals("failure - expected text attribute match", "test",
                createdEntity.getText());

        Collection<Greeting> list = service.findAll();

       // Assert.assertEquals("failure - expected size", 3, list.size());

    }

    @Test
    public void testCreateWithId() {

        Exception exception = null;

        Greeting entity = new Greeting();
        entity.setId(Long.MAX_VALUE);
        entity.setText("test");

        try {
            service.create(entity);
        } catch (Exception e) {
            exception = e;
        }

        Assert.assertNotNull("failure - expected exception", exception);
        /*Assert.assertTrue("failure - expected EntityExistsException",
                exception instanceof EntityExistsException);*/

    }

    @Test
    public void testUpdate() {

        Long id = new Long(1);

        Greeting entity = service.findOne(id);

        Assert.assertNotNull("failure - expected not null", entity);

        String updatedText = entity.getText() + " test";
        entity.setText(updatedText);
        Greeting updatedEntity = service.update(entity);

        Assert.assertNotNull("failure - expected not null", updatedEntity);
        Assert.assertEquals("failure - expected id attribute match", id,
                updatedEntity.getId());
        Assert.assertEquals("failure - expected text attribute match",
                updatedText, updatedEntity.getText());

    }

    @Test
    public void testUpdateNotFound() {

        Exception exception = null;

        Greeting entity = new Greeting();
        entity.setId(Long.MAX_VALUE);
        entity.setText("test");

        try {
            service.update(entity);
        } catch (Exception e) {
            exception = e;
        }

        /*Assert.assertNotNull("failure - expected exception", exception);
        Assert.assertTrue("failure - expected NoResultException",
                exception instanceof NoResultException);*/

    }

    @Test
    public void testDelete() {

        Long id = new Long(1);

        Greeting entity = service.findOne(id);

        Assert.assertNotNull("failure - expected not null", entity);

        service.delete(id);

        Collection<Greeting> list = service.findAll();

        //Assert.assertEquals("failure - expected size", 1, list.size());

        Greeting deletedEntity = service.findOne(id);

        Assert.assertNull("failure - expected null", deletedEntity);

    }
}
