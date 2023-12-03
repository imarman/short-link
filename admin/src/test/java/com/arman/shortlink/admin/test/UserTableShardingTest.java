package com.arman.shortlink.admin.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Arman
 */
@SpringBootTest
public class UserTableShardingTest {

    private static final String USER_TABLE_SQL = """
            CREATE TABLE `t_user_%d` (
              `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
              `username` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名',
              `password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
              `real_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '真实姓名',
              `mobile` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机',
              `email` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
              `deletion_time` bigint DEFAULT NULL COMMENT '注销时间',
              `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
              `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
              `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除表是(0:未删除，1:已删除)',
              PRIMARY KEY (`id`),
              UNIQUE KEY `idx_unique_username` (`username`) USING BTREE
            ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
            """;

    @Test
    public void test() {
        for (int i = 0; i < 16; i++) {
            System.out.printf((USER_TABLE_SQL) + "%n", i);
        }
    }

}
