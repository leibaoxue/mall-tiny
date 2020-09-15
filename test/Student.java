package com.macro.mall.tiny.test;



@Persion(name="haha", age=97, hobby={"test1", "test2"})
public class Student {


    @StudentGender(gender= StudentGender.Gender.BOY)
    private String stuGender;


    public String getStuGender() {
        return stuGender;
    }
    public void setStuGender(String stuGender) {
        this.stuGender = stuGender;
    }
}