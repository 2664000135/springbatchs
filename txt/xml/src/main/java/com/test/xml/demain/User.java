package com.test.xml.demain;

/**
 * @ClassName User
 * @Description 实体类
 * @Author QiaoFoPing
 * @Date 2021/1/7 16:20
 * @Version 1.0
 */
public class User {
    private String id;
    private String name;
    private String age;

    public User(String id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", age=" + age + "]";
    }
}