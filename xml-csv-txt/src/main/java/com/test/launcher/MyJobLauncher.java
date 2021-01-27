package com.test.launcher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName MyJobLauncher
 * @Description
 * @Author QiaoFoPing
 * @Date 2021/1/20 16:07
 * @Version 1.0
 */
@Service
@Slf4j
public class MyJobLauncher {
    private final JobLauncher jobLauncher;
    private final Job job;

    @Autowired
    public MyJobLauncher(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    public JobExecution handle() throws Exception {
        JobExecution execution = jobLauncher.run(job, new JobParameters());
        log.info("com.test.launcher.MyJobLauncher.handle:{}",execution);
        return execution;
    }
}