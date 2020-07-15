package com.example.testtask;

/**
 * Created by Ann Vicheva on 14.07.2020.
 */

public class Profile {

    private int id;
    private String name;
    private int age;
    private String phone_number;
    private String sex;

    //cap for creating objects
    public void Profile(){}

    public void setId(int _id){this.id=_id;}
    public int getId(){return this.id;}

    public void setName(String _name){this.name=_name;}
    public String getName(){return this.name;}

    public void setAge(int _age){this.age=_age;}
    public int getAge(){return this.age;}

    public void setPhone_number(String _phone_number){this.phone_number=_phone_number;}
    public String getPhone_number(){return this.phone_number;}

    public void setSex(String _sex){this.sex=_sex;}
    public String getSex(){return this.sex;}


}
