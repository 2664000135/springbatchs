package com.test;

import com.test.launcher.MyJobLauncher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Assert;


/**
 * @ClassName CsvTxtXmlApplicationTest
 * @Description 测试类
 * @Author QiaoFoPing
 * @Date 2021/1/20 16:28
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableBatchProcessing
public class CsvTxtXmlApplicationTest {
    @Autowired
    private MyJobLauncher jobLauncher;

    @Test
    public void contextLoads() throws Exception {
        JobExecution handle = jobLauncher.handle();
        BatchStatus status = handle.getStatus();
        Assert.assertEquals(status.getBatchStatus(),javax.batch.runtime.BatchStatus.COMPLETED);
    }

}