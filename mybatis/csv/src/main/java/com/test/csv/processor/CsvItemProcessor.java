package com.test.csv.processor;

import com.test.csv.pojo.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;

/**
 * @ClassName CsvItemProcessor
 * @Description CSV文件数据处理及校验
 * @Author QiaoFoPing
 * @Date 2020/12/18 10:55
 * @Version 1.0
 */
public class CsvItemProcessor extends ValidatingItemProcessor<Person> {
    private Logger logger = LoggerFactory.getLogger(CsvItemProcessor.class);

    @Override
    public Person process(Person item) throws ValidationException {
        // 执行super.process()才能调用自定义的校验器
        logger.info("processor start validating...");
        super.process(item);

        //数据处理
        if ("男".equals(item.getGender())){
            item.setGender("M");
        }else {
            item.setGender("F");
        }
        logger.info("processor end validating...");
        return item;
    }

}