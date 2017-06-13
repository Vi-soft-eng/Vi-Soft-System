package com.visoft;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("test")
public abstract class AbstractTest {
    protected Logger LOG = LoggerFactory.getLogger(this.getClass());
}