package com.test.hello.processor;/**
 * @author清梦
 * @site www.xiaomage.com
 * @company xxx公司
 * @create 2020-12-14 13:32
 */

import com.test.hello.pojo.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 *@ClassName PersonItemProcessor
 *@Description TODO
 *@Author QiaoFoPing
 *@Date 2020/12/14 13:32
 *@Version 1.0
 */
public class PersonItemProcessor implements ItemProcessor<Person,String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Person.class);
    @Override
    public String process(Person person) throws Exception {
        String greeting = "Hello " + person.getFirstName() + " " + person.getLastName() + "!";
        LOGGER.info("converting  '{}' into '{}'",person,greeting);
        return greeting;
    }
}
