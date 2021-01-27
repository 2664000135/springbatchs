package com.test.mybatis.mapper;/**
 * @ClassName UserMapper
 * @Description usermapper
 * @Author QiaoFoPing
 * @Date 2020/12/17 15:08
 * @Version 1.0
 */

import com.test.mybatis.dto.UserMap;
import tk.mybatis.mapper.common.Mapper;

/**
 *@ClassName UserMapper
 *@Description TODO
 *@Author QiaoFoPing
 *@Date 2020/12/17 15:08
 *@Version 1.0
 */
@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<UserMap> {
}
