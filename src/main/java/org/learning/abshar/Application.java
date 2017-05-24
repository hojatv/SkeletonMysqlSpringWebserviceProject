package org.learning.abshar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring boot main Application
 */
@SpringBootApplication
@EnableTransactionManagement
public class Application {
    public static void main(String[] args)  throws Exception{
        SpringApplication.run(Application.class,args);
    }
}
