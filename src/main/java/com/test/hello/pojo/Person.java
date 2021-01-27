package com.test.hello.pojo;/**
 * @author清梦
 * @site www.xiaomage.com
 * @company xxx公司
 * @create 2020-12-14 10:40
 */

/**
 *@ClassName Person
 *@Description TODO
 *@Author QiaoFoPing
 *@Date 2020/12/14 10:40
 *@Version 1.0
 */
public class Person {
    private String firstName;
    private String lastName;
    public Person() {
        // default constructor
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
