package com.quartz.processor;

import com.quartz.pojo.User;
import org.springframework.batch.item.ItemProcessor;

/**
 * @ClassName MyProcessor
 * @Description 在process方法中处理User对象，只选择年龄为偶数的User对象传给spring batch的writer类
 * @Author QiaoFoPing
 * @Date 2020/12/24 11:14
 * @Version 1.0
 */
public class MyProcessor implements ItemProcessor<User,User> {
    @Override
    public User process(User user) throws Exception {
        if (Integer.parseInt(user.getAge())%2 ==0){
            return user;
        }
        return null;
    }
}