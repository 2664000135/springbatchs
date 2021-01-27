package com.quartz.config;

import org.quartz.*;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.batch.core.Job;

/**
 * @ClassName QuartzJobLauncher
 * @Description 用quartz进行定时配置job任务
 * @Author QiaoFoPing
 * @Date 2020/12/24 13:40
 * @Version 1.0
 */
public class QuartzJobLauncher extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        String jobName = jobDataMap.getString("jobName");
        JobLauncher jobLauncher = (JobLauncher) jobDataMap.get("jobLauncher");
        JobLocator jobLocator = (JobLocator) jobDataMap.get("jobLocator");
        System.out.println("jobName : " + jobName);
        System.out.println("jobLauncher : " + jobLauncher);
        System.out.println("jobLocator : " + jobLocator);
        JobKey key = jobExecutionContext.getJobDetail().getKey();
        System.out.println(key.getName() + " : " + key.getGroup());

        try {
            Job job = jobLocator.getJob(jobName);
            /*启动spring batch的job*/
            JobExecution jobExecution = jobLauncher.run(job, new JobParameters());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}