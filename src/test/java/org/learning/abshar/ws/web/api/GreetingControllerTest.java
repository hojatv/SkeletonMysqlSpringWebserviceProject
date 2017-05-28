package org.learning.abshar.ws.web.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.learning.abshar.ws.AbstractControllerTest;
import org.learning.abshar.ws.model.Greeting;
import org.learning.abshar.ws.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

//By annotating Transactional, all operations on database are rollbacked after test
@Transactional
public class GreetingControllerTest extends AbstractControllerTest {
    @Autowired
    GreetingService service;

    @Before
    public void setup() {
        super.setup();
        service.evictCache();
    }

    @Test
    public void testGetGreetings() throws Exception {
        String uri = "/api/greetings";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected Http status 200", 200, status);
        Assert.assertTrue("failure - expected Http response to have value", content.length() > 0);
    }

    @Test
    public void testGetGreeting() throws Exception {
        String uri = "/api/greetings/{id}";
        Long id = new Long(1);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON)).andReturn();
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected Http status 200", 200, status);
        Assert.assertTrue("failure - expected Http response to have value", content.length() > 0);
    }

    @Test
    public void testGetGreetingNotFound() throws Exception {
        String uri = "/api/greetings/{id}";
        Long id = Long.MAX_VALUE;
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON)).andReturn();
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected Http status 404", 404, status);
        Assert.assertTrue("failure - expected Http response to have value", content.length() == 0);
    }

    @Test
    public void testCreateGreeting() throws Exception {
        String uri = "/api/greetings";
        Greeting greeting = new Greeting();
        greeting.setText("test");
        String inputJson = mapToJson(greeting);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).
                contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertEquals("- failure - Expected http status 201", 201, status);
        Assert.assertTrue("- failure - expected Http response to have a value", content.trim().length() > 0);

        Greeting createdGreeting = mapFromJson(content,Greeting.class);

        Assert.assertNotNull(" - failure - expected Greeting not null" , createdGreeting);
        Assert.assertNotNull(" - failure - expected greeting.id not null" , createdGreeting.getId());
        Assert.assertNotNull(" - failure - expected greeting.text not null" , createdGreeting.getText());

    }

    @Test
    public void testUpdateGreeting() throws Exception {
        String uri = "/api/greetings";
        Greeting greeting = new Greeting();
        greeting.setText("test");
        greeting.setId(1l);
        String inputJson = mapToJson(greeting);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put(uri).
                contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertEquals("- failure - Expected http status 200", 200, status);
        Assert.assertTrue("- failure - expected Http response to have a value", content.trim().length() > 0);

        Greeting createdGreeting = mapFromJson(content,Greeting.class);

        Assert.assertNotNull(" - failure - expected Greeting not null" , createdGreeting);
        Assert.assertNotNull(" - failure - expected greeting.id not null" , createdGreeting.getId());
        Assert.assertNotNull(" - failure - expected greeting.text not null" , createdGreeting.getText());

    }

    @Test
    public void testDelete() throws Exception{
        String uri =  "/api/greetings/{id}";
        Long id = new Long(1);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri,id)).andReturn();
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals(" -failure - Expected Http status code 204" , 204 , status);
        Assert.assertTrue(" -failure - Expected Http response body to be empty" , content.equals("true"));

        Greeting deletedGreeting = service.findOne(id);
        Assert.assertNull("- failure - Expected greeting be null" , deletedGreeting);
    }



}
