package com.test.config;

import com.test.domain.Person;
import com.test.itemprocess.PersonItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName BatchConfig
 * @Description batch配置
 * @Author QiaoFoPing
 * @Date 2021/1/20 15:05
 * @Version 1.0
 */
@EnableBatchProcessing
@Configuration
public class BatchConfig {
    //执行流程： csv -> txt -> xml
    @Bean
    public FlatFileItemReader<Person> csvItemReader(){
        FlatFileItemReader<Person> csvItemReader = new FlatFileItemReader<>();
        //setResource 指定文件资源的位置：通过ClassPathResource（类所在路径）
        // 或者FileSystemResource（文件系统所在路径）来指定要读取的文件
        csvItemReader.setResource(new ClassPathResource("data/sample-data.csv"));
        /*setLineMapper 行映射：指定行与实体对象之间的映射关系，示例代码使用了DefaultLineMapper*/
        csvItemReader.setLineMapper(new DefaultLineMapper<Person>(){{
            setLineTokenizer(new DelimitedLineTokenizer(){{
                setNames(new String[]{"name","age"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>(){{
                setTargetType(Person.class);
            }});
        }});
        return csvItemReader;
    }

    @Bean
    public FlatFileItemWriter<Person> txtItemWriter(){
        FlatFileItemWriter<Person> txtItemWriter = new FlatFileItemWriter<>();
        txtItemWriter.setAppendAllowed(true);
        txtItemWriter.setEncoding("UTF-8");
        txtItemWriter.setResource(new ClassPathResource("data/sample-data.txt"));
        /*setLineAggregator 和 FlatFileItemReader 的setLineMapper方法有着相似之处，
        setLineAggregator方法是将对象属性聚合为字符串，聚合时根据需要设置分隔符（setDelimiter），
        以及对象属性对应的字符名称（setFieldExtractor）

        DelimitedLineAggregator 继承 ExtractorLineAggregator。
        是一种更常使用的聚合方式、将数组用指定符号分割，默认使用逗号
        */

        txtItemWriter.setLineAggregator(new DelimitedLineAggregator<Person>(){{
            setDelimiter(",");
            /*ExtractorLineAggregator 是抽象类实现LineAggregator接口。
            使用 FieldExtractor将对象属性转换为数组，该类的扩展类负责将数组转换字符串（doAggregate）
             */
            setFieldExtractor(new BeanWrapperFieldExtractor<Person>(){{
                setNames(new String[]{"name","age"});
            }});
        }});
        return txtItemWriter;
    }

    @Bean
    public StaxEventItemWriter<Person> xmlItemWriter() {
        StaxEventItemWriter<Person> xmlItemWriter = new StaxEventItemWriter<>();
        xmlItemWriter.setRootTagName("root");
        xmlItemWriter.setSaveState(true);
        xmlItemWriter.setEncoding("UTF-8");
        xmlItemWriter.setResource(new ClassPathResource("/data/sample-data.xml"));
        xmlItemWriter.setMarshaller(new XStreamMarshaller() {{
            Map<String, Class<Person>> map = new HashMap<>();
            map.put("person",Person.class);
            setAliases(map);
        }});
        return xmlItemWriter;
    }

    @Bean
    public Job flatFileJob(JobBuilderFactory jobBuilderFactory, Step stepCsv2Txt, Step stepTxt2Xml) {
        return jobBuilderFactory.get("flatFileJob")
                .incrementer(parameters -> {
                    Map<String, JobParameter> parameterMap = parameters.getParameters();
                    parameterMap.put("key", new JobParameter(UUID.randomUUID().toString()));
                    return parameters;
                })
                .start(stepCsv2Txt)
                .next(stepTxt2Xml)
                .build();
    }

    @Bean
    public Step stepCsv2Txt(StepBuilderFactory stepBuilderFactory, PersonItemProcessor processor,
                            ItemReader csvItemReader, ItemWriter txtItemWriter) {
        TaskletStep stepCsv2Txt = stepBuilderFactory.get("stepCsv2Txt")
                .<Person, Person>chunk(10)
                .reader(csvItemReader)
                .processor(processor)
                .writer(txtItemWriter)
                .build();
        return stepCsv2Txt;
    }

    @Bean
    public Step stepTxt2Xml(StepBuilderFactory stepBuilderFactory, PersonItemProcessor processor,
                            ItemReader txtItemReader, ItemWriter xmlItemWriter) {
        TaskletStep stepTxt2Xls = stepBuilderFactory.get("stepTxt2Xml")
                .<Person, Person>chunk(10)
                .reader(txtItemReader)
                .processor(processor)
                .writer(xmlItemWriter)
                .build();
        return stepTxt2Xls;
    }
}