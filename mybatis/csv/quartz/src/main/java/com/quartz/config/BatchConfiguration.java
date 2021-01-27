package com.quartz.config;

import com.quartz.pojo.User;
import com.quartz.processor.MyProcessor;
import com.quartz.reader.MyReader;
import com.quartz.writer.MyWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.jws.soap.SOAPBinding;

/**
 * @ClassName BatchConfiguration
 * @Description 创建job并配置job
 * @Author QiaoFoPing
 * @Date 2020/12/24 13:31
 * @Version 1.0
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    /*创建job*/
    public Job jobMethod(){
        return jobBuilderFactory.get("myJod")
                .start(stepMethod())
                .build();
    }

    @Bean
    /*创建step*/
    public Step stepMethod() {
        return stepBuilderFactory.get("myStep")
                .<User,User>chunk(3)
                .reader(new MyReader())
                .processor(new MyProcessor())
                .writer(new MyWriter())
                .allowStartIfComplete(true)
                .build();
    }

}