package com.shin.springbootinf.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Samplecontroller {

    private static Logger LOGGER = LoggerFactory.getLogger(Samplecontroller.class);

    @Autowired
    private SampleService sampleService;

    @GetMapping("/hello")
    public String hello() {
        LOGGER.info("shinilhyun logger");
        System.out.println("skip");
        return "hello " + sampleService.getName();
    }
}
