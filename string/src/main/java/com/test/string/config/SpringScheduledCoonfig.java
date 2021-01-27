package com.test.string.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName SpringScheduledCoonfig
 * @Description 定时任务配置
 * @Author QiaoFoPing
 * @Date 2020/12/23 15:56
 * @Version 1.0
 */
@Component
public class SpringScheduledCoonfig {
    @Autowired
    private Job singleStepJod;
    @Autowired
    private JobLauncher jobLauncher;

    @Scheduled(cron = "0/5 * * * * ?")
    public void demoScheduled() throws Exception{
        JobParameters jobParameters = new JobParametersBuilder().addLong("time",System.currentTimeMillis()).toJobParameters();
        jobLauncher.run(singleStepJod,jobParameters);
    }
}