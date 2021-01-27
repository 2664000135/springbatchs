package com.test.string.step;/**
 * @author清梦
 * @site www.xiaomage.com
 * @company xxx公司
 * @create 2020-12-15 14:28
 */

import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 *@ClassName Writer
 *@Description TODO
 *@Author QiaoFoPing
 *@Date 2020/12/15 14:28
 *@Version 1.0
 */
public class Writer implements ItemWriter<String> {
    @Override
    public void write(List<? extends String> list) throws Exception {
        for (String msg:list){
            System.out.println("Writing the data:"+msg);
        }
    }
}
