package com.test.string;/**
 * @author清梦
 * @site www.xiaomage.com
 * @company xxx公司
 * @create 2020-12-15 16:02
 */

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *@ClassName SpringBatchApplicaton
 *@Description TODO
 *@Author QiaoFoPing
 *@Date 2020/12/15 16:02
 *@Version 1.0
 */
@EnableScheduling
@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBatchApplication.class,args);
    }
}
