package com.test.xml.processor;

import com.test.xml.demain.User;
import org.springframework.batch.item.ItemProcessor;

/**
 * @ClassName UserItemProcessor
 * @Description 对每条数据进行处理，实现ItemProcessor即可
 * @Author QiaoFoPing
 * @Date 2021/1/7 16:22
 * @Version 1.0
 */
public class UserItemProcessor implements ItemProcessor<User,User> {
    @Override
    public User process(User user) throws Exception {
        if (Integer.parseInt(user.getAge())%2==0){
            return user;
        }
        return null;
    }
}