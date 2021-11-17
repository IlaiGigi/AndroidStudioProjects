package com.example.identification;

public class User {
    private String username;
    private String password;
    private String age;
    private String mail;
    public User(String username, String password, String age, String mail){
        this.username = username;
        this.password = password;
        this.age = age;
        this.mail = mail;
    }
    //Getters
    public String getUsername(){return this.username;}
    public String getPassword(){return this.password;}
    public String getAge(){return this.age;}
    public String getMail(){return this.mail;}
    //Setters
    public void setUsername(String username){this.username = username;}
    public void setPassword(String password){this.password = password;}
    public void setAge(String age){this.age = age;}
    public void setMail(String mail){this.age = age;}
}
