package com.example.finalproject;

public class User {
    private String username;
    private String password;
    private int coins;
    public User(String username, String password, int coins){
        this.username = username;
        this.password = password;
        this.coins = coins;
    }
    //Getters
    public String getUsername(){return this.username;}
    public String getPassword(){return this.password;}
    public int getCoins(){return this.coins;}
    //Setters
    public void setUsername(String username){this.username = username;}
    public void setPassword(String password){this.password = password;}
    public void setCoins(int coins){this.coins = coins;}
}