package com.test.mybatis.service;

import com.test.mybatis.dto.UserMap;
import com.test.mybatis.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserService
 * @Description 用户service
 * @Author QiaoFoPing
 * @Date 2020/12/17 15:07
 * @Version 1.0
 */
@Service
public class UserService {
    private UserMapper userMapper;

    public UserMap selectOne(int id){
       return userMapper.selectByPrimaryKey(id);
    }
}