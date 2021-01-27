package com.test.mybatis.processor;

import com.test.mybatis.dto.UserMap;
import org.springframework.batch.item.ItemProcessor;

import java.text.SimpleDateFormat;

/**
 * @ClassName userMapItemProcessor
 * @Description
 * @Author QiaoFoPing
 * @Date 2020/12/17 14:43
 * @Version 1.0
 */
public class UserMapItemProcessor implements ItemProcessor<UserMap,UserMap> {

    private ProcessStatus processStatus;


    public UserMapItemProcessor(ProcessStatus processStatus) {
        this.processStatus = processStatus;
    }


    @Override
    public UserMap process(UserMap userMap) throws Exception {
        int id = userMap.getId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return null;
    }




    public enum ProcessStatus {
        IMPORT,
        UPDATE;
    }

    public static void main(String[] args) {
        System.out.println("开始");
    }
}