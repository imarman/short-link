package com.arman.shortlink.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Arman
 */
@MapperScan("com.arman.shortlink.admin.dao.mapper")
@SpringBootApplication(scanBasePackages = {"com.arman.shortlink.admin", "com.arman.shortlink.common"})
public class SortLinkAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SortLinkAdminApplication.class, args);
    }
}
