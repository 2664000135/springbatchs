package com.test.xml.mapper;

import com.test.xml.demain.User;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * @ClassName UserFieldSetMapper
 * @Description spring batch提供了FlatFileItemReader，用来读取文件，把每条数据映射到User对象中，通过下面方式映射
 * @Author QiaoFoPing
 * @Date 2021/1/7 15:56
 * @Version 1.0
 */
public class UserFieldSetMapper implements FieldSetMapper<User> {
    @Override
    public User mapFieldSet(FieldSet fieldSet) throws BindException {
        return new User(fieldSet.readString("ID"),
                fieldSet.readString("NAME"),
                fieldSet.readString("AGE"));
    }
}