package com.test.mybatis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.modeler.BaseModelMBean;

import java.util.Date;

/**
 * @ClassName UserMap
 * @Description 用户
 * @Author QiaoFoPing
 * @Date 2020/12/17 13:59
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserMap {
    private int id;
    private String name;
    private String gender;
    private Date createTime;

}