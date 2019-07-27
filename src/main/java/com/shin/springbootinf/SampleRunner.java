package com.shin.springbootinf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleRunner implements ApplicationRunner {
    @Autowired
    ShinProperties shinProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("=========================");
        System.out.println(shinProperties.getName());
        System.out.println(shinProperties.getAge());
        System.out.println(shinProperties.getFullName());
        System.out.println("=========================");
    }
}
