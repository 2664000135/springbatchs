package com.test.string.step;/**
 * @author清梦
 * @site www.xiaomage.com
 * @company xxx公司
 * @create 2020-12-15 14:25
 */

import org.springframework.batch.item.ItemProcessor;

/**
 *@ClassName Processor
 *@Description 将字符串转为大写
 *@Author QiaoFoPing
 *@Date 2020/12/15 14:25
 *@Version 1.0
 */
public class Processor implements ItemProcessor<String,String> {
    @Override
    public String process(String s) throws Exception {
        return s.toUpperCase();
    }
}
