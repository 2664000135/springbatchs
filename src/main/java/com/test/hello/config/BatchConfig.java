package com.test.hello.config;/**
 * @author清梦
 * @site www.xiaomage.com
 * @company xxx公司
 * @create 2020-12-14 10:42
 */

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 *@ClassName BatchConnfig
 *@Description TODO
 *@Author QiaoFoPing
 *@Date 2020/12/14 10:42
 *@Version 1.0
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer {

    @Override
    public void setDataSource(DataSource dataSource) {
      /*  super.setDataSource(dataSource);
      * 为了使Spring Batch使用基于Map的JobRepository，我们需要扩展DefaultBatchConfigurer。
      * 重写setDataSource()方法以不设置DataSource。
      * 这将导致自动配置使用基于Map的JobRepository。
      * */

    }
}
