package com.test.string.step;/**
 * @author清梦
 * @site www.xiaomage.com
 * @company xxx公司
 * @create 2020-12-15 14:18
 */

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 *@ClassName Reader
 *@Description 从数组中读取3个字符串
 *@Author QiaoFoPing
 *@Date 2020/12/15 14:18
 *@Version 1.0
 */
public class Reader implements ItemReader<String> {
    private String message[]={"I need money","I want to have a girlfriend","I believe i can fly!"};
    private int count  = 0;
    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (count < message.length){
            return message[count++];
        }else {
            count = 0;
        }
        return null;

    }
}
