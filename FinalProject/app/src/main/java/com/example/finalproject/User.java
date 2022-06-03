package com.example.finalproject;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String username;
    public String password;
    public int coins;
    public int shares;
    public boolean sound;
    public ArrayList<Integer> kidsLevels;
    public ArrayList<Integer> classicLevels;

    public User(String username, String password, int coins, int shares, boolean sound){
        this.username = username;
        this.password = password;
        this.coins = coins;
        this.shares = shares;
        this.sound = sound;
        kidsLevels = new ArrayList<>();
        kidsLevels.add(0);
        kidsLevels.add(0);
        kidsLevels.add(0);
        classicLevels = new ArrayList<>();
        classicLevels.add(0);
        classicLevels.add(0);
        classicLevels.add(0);
    }

    public User(){}
    //Getters
    public String getUsername(){return this.username;}
    public String getPassword(){return this.password;}
    public int getCoins(){return this.coins;}
    public int getShares(){return this.shares;}
    public boolean isSound(){return this.sound;}
    public ArrayList<Integer> getKidsLevels(){return this.kidsLevels;}
    public ArrayList<Integer> getClassicLevels(){return this.classicLevels;}
    //Setters
    public void setUsername(String username){this.username = username;}
    public void setPassword(String password){this.password = password;}
    public void setCoins(int coins){this.coins = coins;}
    public void setShares(int shares){this.shares = shares;}
    public void setSound(boolean state){this.sound = state;}
    public void setKidsLevels(ArrayList<Integer> levels){this.kidsLevels = levels;}
    public void setClassicLevels(ArrayList<Integer> levels){this.classicLevels = levels;}
}