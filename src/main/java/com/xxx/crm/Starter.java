package com.xxx.crm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ：刘彬
 * @date ：Created in 2023/3/6 17:28
 * @description：
 */
@SpringBootApplication
@MapperScan("com.xxx.crm.dao")
public class Starter {
    public static void main(String[] args) {
        SpringApplication.run(Starter.class);
    }
}
