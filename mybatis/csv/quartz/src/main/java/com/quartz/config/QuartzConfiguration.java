package com.quartz.config;

import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName QuartzConfiguration
 * @Description 创建定时器，定时触发spring batch 的job任务
 * @Author QiaoFoPing
 * @Date 2020/12/24 16:15
 * @Version 1.0
 */
@Configuration
public class QuartzConfiguration {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobLocator jobLocator;

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry){
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }

    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean(){
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(QuartzJobLauncher.class);
        jobDetailFactoryBean.setGroup("my_group");
        jobDetailFactoryBean.setName("my_job");
        Map<String,Object>map = new HashMap<>();
        map.put("jobName","myJod");
        map.put("jobLauncher",jobLauncher);
        map.put("jobLocator",jobLocator);
        jobDetailFactoryBean.setJobDataAsMap(map);
        return jobDetailFactoryBean;
    }

    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean(){
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        System.out.println("-------:"+jobDetailFactoryBean().getObject());
        cronTriggerFactoryBean.setJobDetail(jobDetailFactoryBean().getObject());
        cronTriggerFactoryBean.setStartDelay(5000);
        cronTriggerFactoryBean.setName("my_trigger");
        cronTriggerFactoryBean.setGroup("trigger_group");
        cronTriggerFactoryBean.setCronExpression("0/3 * * * * ?");
        return cronTriggerFactoryBean;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(cronTriggerFactoryBean().getObject());
        return schedulerFactoryBean;
    }
}