package com.quartz.writer;

import com.quartz.pojo.User;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * @ClassName MyWriter
 * @Description 打印出处理类处理后的User对象
 * @Author QiaoFoPing
 * @Date 2020/12/24 11:21
 * @Version 1.0
 */
public class MyWriter implements ItemWriter<User> {
    @Override
    public void write(List<? extends User> list) throws Exception {
        for (User user:list){
            System.out.println(user);
        }
    }
}