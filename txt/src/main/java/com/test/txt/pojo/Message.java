package com.test.txt.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @ClassName Message
 * @Description 实体类
 * @Author QiaoFoPing
 * @Date 2021/1/5 16:14
 * @Version 1.0
 */
@Entity
@Table(name = "message")
@Data
public class Message {
    @Id
    @Column(name = "object_id",nullable = false)
    private String objectId;

    @Column(name = "content")
    private String content;

    @Column(name = "last_modified_time")
    private LocalDateTime lastModifiedTime;

    @Column(name = "created_time")
    private LocalDateTime createdTime;



}