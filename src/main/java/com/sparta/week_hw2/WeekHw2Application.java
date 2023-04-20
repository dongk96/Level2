package com.sparta.week_hw2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WeekHw2Application {

    public static void main(String[] args) {
        SpringApplication.run(WeekHw2Application.class, args);
    }

}
