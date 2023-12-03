package com.arman.shortlink.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Arman
 */
@MapperScan("com.arman.shortlink.core.dao.mapper")
@SpringBootApplication(scanBasePackages = {"com.arman.shortlink.core", "com.arman.shortlink.common"})
public class SortLinkCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(SortLinkCoreApplication.class, args);
    }
}
