package com.shin.springbootinf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * type safe 하게 properties 사용
 */
@Component
@ConfigurationProperties("shin")
@Validated
public class ShinProperties {
    @NotEmpty
    private String name;
    private int age;
    private String fullName;

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration sesstionTimeout = Duration.ofSeconds(30);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Duration getSesstionTimeout() {
        return sesstionTimeout;
    }

    public void setSesstionTimeout(Duration sesstionTimeout) {
        this.sesstionTimeout = sesstionTimeout;
    }
}
