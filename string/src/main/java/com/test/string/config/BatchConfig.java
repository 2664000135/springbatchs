package com.test.string.config;/**
 * @author清梦
 * @site www.xiaomage.com
 * @company xxx公司
 * @create 2020-12-15 15:08
 */

import com.test.string.listener.JobCompletionListener;
import com.test.string.step.Processor;
import com.test.string.step.Reader;
import com.test.string.step.Writer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *@ClassName BatchConfig
 *@Description TODO
 *@Author QiaoFoPing
 *@Date 2020/12/15 15:08
 *@Version 1.0
 */
@Configuration
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job processorJob(){
        return jobBuilderFactory.get("processorJob")
                .incrementer(new RunIdIncrementer()).listener(listener())
                .flow(orderStep1()).end().build();
    }



    @Bean
    public Step orderStep1() {
        return stepBuilderFactory.get("orderStep1").<String,String> chunk(1)
                .reader(new Reader()).processor(new Processor())
                .writer(new Writer()).build();
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobCompletionListener();
    }
}
