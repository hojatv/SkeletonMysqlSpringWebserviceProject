package org.learning.abshar.ws;

import org.junit.runner.RunWith;
import org.learning.abshar.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public abstract class AbstractTest {
    Logger logger = LoggerFactory.getLogger(AbstractTest.class);
}
