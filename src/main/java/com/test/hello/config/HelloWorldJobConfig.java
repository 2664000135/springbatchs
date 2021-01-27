package com.test.hello.config;/**
 * @author清梦
 * @site www.xiaomage.com
 * @company xxx公司
 * @create 2020-12-14 10:54
 */

import com.test.hello.pojo.Person;
import com.test.hello.processor.PersonItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

/**
 *@ClassName HelloWorldJobConfig
 *@Description TODO
 *@Author QiaoFoPing
 *@Date 2020/12/14 10:54
 *@Version 1.0
 */
@Configuration
public class HelloWorldJobConfig {

    @Bean
    public Job helloWorldJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory){
        return jobBuilderFactory.get("helloWorldJob")
                .start(helloWorldStep(stepBuilderFactory)).build();
    }

    @Bean
    public Step helloWorldStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("HelloWorldStep")
                .<Person,String>chunk(10).reader(reader())
                //使用chunk()，我们指定每个事务中处理的项的数量
                //Chunk还指定步骤的输入(Person)和输出(String)类型。
                .processor(processor()).writer(writer()).build();
    }




    @Bean
    public FlatFileItemReader<Person> reader() {
        //使用FlatFileItemReader读取person CSV文件。这个类提供了读取和解析CSV文件的基本功能
        return new FlatFileItemReaderBuilder<Person>()
        /*有一个FlatFileItemReaderBuilder实现，
        它允许我们创建一个FlatFileItemReader。
        我们首先指定读取文件中每一行的结果是Person对象。
        然后，我们将使用name()方法为FlatFileItemReader添加一个名称，
        并指定需要读取的资源(在本例中是persons.csv文件)。*/
                .name("personItemReader")
                .resource(new ClassPathResource("csv/person.csv"))
                /*为了让FlatFileItemReader处理我们的文件，
                我们需要指定一些额外的信息。
                首先，我们定义文件中的数据是带分隔符的(默认为逗号作为分隔符)。
                * */
                .delimited().names(new String[] {"firstName","lastName"})
                /*我们还指定了如何将一行中的每个字段映射到Person对象。
                这是使用names()来完成的，通过将名称与对象上的setter匹配，可以使Spring Batch映射字段。
                在本文的例子中，一行的第一个字段将使用firstName setter进行映射。
                为了实现这一点，我们还需要指定targetType,即Person对象。*/
                .targetType(Person.class).build();
    }

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean
    public FlatFileItemWriter<String> writer() {
        /*我们使用FlatFileItemWriterBuilder实现来创建一个FlatFileItemWriter。
        我们为writer添加一个名称，并指定需要将数据写入其中的资源(在本例中是greeting.txt文件)。*/
        return new FlatFileItemWriterBuilder<String>()
                .name("greetingItemWriter")
                .resource(new FileSystemResource( "target/test-outputs/greetings.txt"))
                .lineAggregator(new PassThroughLineAggregator<>()).build();
        /*FlatFileItemWriter需要知道如何将生成的输出转换成可以写入文件的单个字符串。
        在本例中，我们的输出已经是一个字符串，我们可以使用PassThroughLineAggregator。
        这是最基本的实现，它假定对象已经是一个字符串。*/
    }



}
