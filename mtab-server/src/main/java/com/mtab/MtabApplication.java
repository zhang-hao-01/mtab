package com.mtab;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * mTab启动类
 *
 * @author zhanghao
 */
@SpringBootApplication
@MapperScan("com.mtab.mapper")
public class MtabApplication {

    public static void main(String[] args) {
        SpringApplication.run(MtabApplication.class, args);
    }

}
