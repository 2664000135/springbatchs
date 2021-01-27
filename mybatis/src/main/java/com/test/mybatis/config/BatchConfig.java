package com.test.mybatis.config;

import com.test.mybatis.dto.UserMap;
import com.test.mybatis.listener.JobCompletionNotificationListener;
import com.test.mybatis.processor.UserMapItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

import static com.test.mybatis.processor.UserMapItemProcessor.ProcessStatus.IMPORT;

/**
 * @ClassName BatchConfig
 * @Description 批处理配置类
 * @Author QiaoFoPing
 * @Date 2020/12/17 14:03
 * @Version 1.0
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    DataSource dataSource;

    @Bean
    public FlatFileItemReader<UserMap> reader(){
        FlatFileItemReader<UserMap> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("F:\\user.txt"));
        reader.setLineMapper(new DefaultLineMapper<UserMap>(){{
            setLineTokenizer(new DelimitedLineTokenizer("|") {{
                setNames(new String[] {"id","name","gender","createTime"});
            }});
        }});
        return reader;
    }

    @Bean
    public JdbcBatchItemWriter<UserMap> importWriter(){
        JdbcBatchItemWriter<UserMap> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO tb_user (id,name,gender,create_time) VALUES (:id, :name,:gender,:createTime)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public JdbcBatchItemWriter<UserMap> updateWriter(){
        JdbcBatchItemWriter<UserMap> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("update tb_user set name = (:name),gender = (:gender),create_time = (:createTime)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public UserMapItemProcessor processor(UserMapItemProcessor.ProcessStatus processStatus){
        return new UserMapItemProcessor(processStatus);
    }


    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(importStep())
                .end()
                .build();
    }
    @Bean
    public Step importStep() {
        return stepBuilderFactory.get("importStep")
                .<UserMap, UserMap>chunk(100)
                .reader(reader())
                .processor(processor(IMPORT))
                .writer(importWriter())
                .build();
    }
}