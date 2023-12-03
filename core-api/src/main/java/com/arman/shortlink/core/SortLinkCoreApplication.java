package com.arman.shortlink.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Arman
 */
@MapperScan("com.arman.shortlink.core.dao.mapper")
@SpringBootApplication
public class SortLinkCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(SortLinkCoreApplication.class, args);
    }
}
