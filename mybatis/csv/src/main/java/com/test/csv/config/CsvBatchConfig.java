package com.test.csv.config;

import com.test.csv.listener.CsvJobListener;
import com.test.csv.pojo.Person;
import com.test.csv.processor.CsvItemProcessor;
import com.test.csv.validator.CsvBeanValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.print.DocFlavor;
import javax.sql.DataSource;

/**
 * @ClassName CsvBatchConfig
 * @Description spring batch csv文件批处理配置
 * @Author QiaoFoPing
 * @Date 2020/12/18 10:25
 * @Version 1.0
 */
@Configuration
@EnableBatchProcessing //开启批处理的支持
@Import(DruidDBConfig.class)//注入datasource
public class CsvBatchConfig {
    private Logger logger = LoggerFactory.getLogger(CsvBatchConfig.class);

    /**
     * @description ItemReader定义：读取文件数据+entirty映射
     * @author  Qiaofoping
     * @date    2020/12/18 10:28
     * @param
     * @return  org.springframework.batch.item.ItemReader<Person>
     */
    @Bean
    public ItemReader<Person> reader(){
        // 使用FlatFileItemReader去读cvs文件，一行即一条数据
        FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
        // 设置文件处在路径
        reader.setResource(new ClassPathResource("person.csv"));
        // entity与csv数据做映射
        reader.setLineMapper(new DefaultLineMapper<Person>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[]{"id", "name", "age", "gender"});
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {
                    {
                        setTargetType(Person.class);
                    }
                });
            }
        });
        return reader;
    }



    /**
     * @description 注册ItemProcessor：处理数据加校验数据
     * @author  Qiaofoping
     * @date    2020/12/18 10:52
     * @param
     * @return  org.springframework.batch.item.ItemProcessor<com.test.csv.pojo.Person,com.test.csv.pojo.Person>
     */
    @Bean
    public ItemProcessor<Person,Person> processor(){
        CsvItemProcessor csvItemProcessor = new CsvItemProcessor();
        // 设置校验器
        csvItemProcessor.setValidator(csvBeanValidator());
        return csvItemProcessor;
    }

    /**
     * 注册校验器
     * @return
     */
    private CsvBeanValidator csvBeanValidator() {
        return new CsvBeanValidator<Person>();
    }


    /**
     * ItemWriter定义：指定datasource，设置批量插入sql语句，写入数据库
     * @param dataSource
     * @return
     */
    @Bean
    public ItemWriter<Person> writer(DataSource dataSource){
        //使用jdbcBatchItemWriter写数据到数据库中
        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<>();
        //设置有参数的sql语句
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
        String sql = "insert into person values(:id,:name,:age,:gender)";
        writer.setSql(sql);
        writer.setDataSource(dataSource);
        return writer;
    }
    /**
     * @description JobRepository定义：设置数据库，注册Job容器
     * @author  Qiaofoping
     * @date    2020/12/18 14:47
     * @param	dataSource
     * @param	transactionManager
     * @return  org.springframework.batch.core.repository.JobRepository
     */
    @Bean
    public JobRepository csvJobRepository(DataSource dataSource, PlatformTransactionManager transactionManager)throws Exception{
        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setDatabaseType("mysql");
        jobRepositoryFactoryBean.setTransactionManager(transactionManager);
        jobRepositoryFactoryBean.setDataSource(dataSource);
        return jobRepositoryFactoryBean.getObject();
    }


    /**
     * @description jobLauncher定义：
     * @author  Qiaofoping
     * @date    2020/12/18 14:59
     * @param	dataSource
     * @param	transactionManager
     * @return  org.springframework.batch.core.launch.support.SimpleJobLauncher
     */
    @Bean
    public SimpleJobLauncher csvJobLauncher(DataSource dataSource,PlatformTransactionManager transactionManager)throws Exception{
      SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
      //设置jobRepository
        jobLauncher.setJobRepository(csvJobRepository(dataSource,transactionManager));
        return jobLauncher;
    }

    /**
     * 定义job
     * @param jobs
     * @param step
     * @return
     */
    @Bean
    public Job importJob(JobBuilderFactory jobs, Step step){
        return jobs.get("importCsvJob")
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end()
                .listener(csvJobListener())
                .build();
    }


    /**
     * 注册job监听器
     * @return
     */
    @Bean
    public CsvJobListener csvJobListener() {
        return new CsvJobListener();
    }


    /**
     * step定义：步骤包括ItemReader->ItemProcessor->ItemWriter 即读取数据->处理校验数据->写入数据
     * @param stepBuilderFactory
     * @param reader
     * @param writer
     * @param processor
     * @return
     */
    @Bean
    public Step step(StepBuilderFactory stepBuilderFactory,ItemReader<Person> reader,ItemWriter<Person> writer,ItemProcessor<Person,Person> processor){
        return stepBuilderFactory.get("step")
                // Chunk的机制(即每次读取一条数据，再处理一条数据，
                // 累积到一定数量后再一次性交给writer进行写入操作)
                .<Person,Person>chunk(65000)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}