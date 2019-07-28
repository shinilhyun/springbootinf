package com.shin.springbootinf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleRunner implements ApplicationRunner {

    private final static Logger LOGGER = LoggerFactory.getLogger(SampleRunner.class);

    @Autowired
    private String hello;

    @Autowired
    ShinProperties shinProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOGGER.debug("=========================");
        LOGGER.debug(hello);
        LOGGER.debug(shinProperties.getName());
        LOGGER.debug(shinProperties.getFullName());
        LOGGER.debug("=========================");
    }
}
