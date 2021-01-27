package com.test.txt.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName JobListener
 * @Description 一个简单的JOB listener
 * @Author QiaoFoPing
 * @Date 2021/1/6 11:00
 * @Version 1.0
 */
@Component
public class JobListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(JobListener.class);
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private long startTime;
    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime = System.currentTimeMillis();
        log.info("job before" + jobExecution.getJobParameters());

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("JOB STATUS:{}",jobExecution.getStatus());
        if (jobExecution.getStatus() == BatchStatus.COMPLETED){
            log.info("JOB FINISHED");
            threadPoolTaskExecutor.destroy();
        }else {
            log.info("JOB FAILED");
        }
        log.info("Job Cost Time : {}ms" , (System.currentTimeMillis() - startTime));
    }
}