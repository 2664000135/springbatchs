package com.test.string.listener;/**
 * @author清梦
 * @site www.xiaomage.com
 * @company xxx公司
 * @create 2020-12-15 14:32
 */

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

/**
 *@ClassName JobCompletionListener
 *@Description 监听：任务完成后往控制台输出一行字符串
 *@Author QiaoFoPing
 *@Date 2020/12/15 14:32
 *@Version 1.0
 */

public class JobCompletionListener extends JobExecutionListenerSupport {
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED){
            System.out.println("BATCH JOB COMPETED SUCCESSFULLY");
        }
    }
}
