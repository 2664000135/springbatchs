package com.test.csv.pojo;

import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @ClassName Person
 * @Description
 * @Author QiaoFoPing
 * @Date 2020/12/18 10:32
 * @Version 1.0
 */

public class Person implements Serializable {
    private final long serialVersionUID = 1L;

    private String id;
    @Size(min = 2, max = 8)
    private String name;
    private int age;
    private String gender;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}