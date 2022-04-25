package com.example.finalproject;

public class User {
    private String username;
    private String password;
    private int coins;
    private int shared;
    public User(String username, String password, int coins, int shared){
        this.username = username;
        this.password = password;
        this.coins = coins;
        this.shared = shared;
    }
    //Getters
    public String getUsername(){return this.username;}
    public String getPassword(){return this.password;}
    public int getCoins(){return this.coins;}
    public int getShares(){return this.shared;}
    //Setters
    public void setUsername(String username){this.username = username;}
    public void setPassword(String password){this.password = password;}
    public void setCoins(int coins){this.coins = coins;}
    public void setShares(int shares){this.shared = shares;}
}