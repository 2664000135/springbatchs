package com.test.csv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName BatchTest
 * @Description 测试
 * @Author QiaoFoPing
 * @Date 2020/12/18 15:37
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BatchTest {
    @Autowired
    Job importJob;

    @Autowired
    SimpleJobLauncher jobLauncher;

    @Test
    public void test1()throws Exception{
// 后置参数：使用JobParameters中绑定参数
        JobParameters jobParameters = new JobParametersBuilder().addLong("time",System.currentTimeMillis()).toJobParameters();
        jobLauncher.run(importJob,jobParameters);
    }
}