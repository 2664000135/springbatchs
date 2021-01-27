package com.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName CsvTxtXmlApplication
 * @Description 启动类
 * @Author QiaoFoPing
 * @Date 2021/1/20 16:12
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("com.test.*")
public class CsvTxtXmlApplication {
    public static void main(String[] args) {
        SpringApplication.run(CsvTxtXmlApplication.class,args);
    }
}