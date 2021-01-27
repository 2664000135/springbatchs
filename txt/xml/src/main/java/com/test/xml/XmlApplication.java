package com.test.xml;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName Xmlapplication
 * @Description 启动jod
 * @Author QiaoFoPing
 * @Date 2021/1/7 16:39
 * @Version 1.0
 */
public class XmlApplication {
    public static void main(String[] args) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("job.xml");

        SimpleJobLauncher launcher = (SimpleJobLauncher) ctx.getBean("laucher");
        Job job = (Job) ctx.getBean("jobExample");
        System.out.println(launcher);
        System.out.println(job);
        launcher.run(job, new JobParameters());
        ctx.close();

    }
}