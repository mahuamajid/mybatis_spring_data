package com.mahua.springdatajpamybatisdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mahua.springdatajpamybatisdemo.mapping")
public class SpringDataJpaMybatisDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaMybatisDemoApplication.class, args);
    }

}
