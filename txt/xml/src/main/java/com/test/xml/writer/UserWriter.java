package com.test.xml.writer;

import com.test.xml.demain.User;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * @ClassName UserWriter
 * @Description 实现ItemWriter即可
 * @Author QiaoFoPing
 * @Date 2021/1/7 16:26
 * @Version 1.0
 */
public class UserWriter implements ItemWriter<User> {
    @Override
    public void write(List<? extends User> list) throws Exception {
        for (User user:list){
            System.out.println(user);
        }
    }
}