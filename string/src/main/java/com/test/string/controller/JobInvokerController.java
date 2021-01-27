package com.test.string.controller;/**
 * @author清梦
 * @site www.xiaomage.com
 * @company xxx公司
 * @create 2020-12-15 15:46
 */

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@ClassName JobInvokerController
 *@Description TODO
 *@Author QiaoFoPing
 *@Date 2020/12/15 15:46
 *@Version 1.0
 */
@RestController
public class JobInvokerController {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    @RequestMapping("/invokeJob")
    public String handle() throws Exception{
        JobParameters jobParameters = new JobParametersBuilder().addLong("time",System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(job,jobParameters);
        return "Batch Job has been invoked";
    }
}
