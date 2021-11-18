package com.example.firsttest;

import android.graphics.Point;

public class Soldier {
    private int teamNumber; //1/2
    private char type;
    private Point position;
    private boolean selected;
    public Soldier(int teamNumber, char type, Point position){
        this.teamNumber = teamNumber;
        this.type = type;
        this.position = position;
        this.selected = false;
    }
    public void moveTo(Point newPos){
        this.position = newPos;
    }
    public int getTeamNumber(){return this.teamNumber;}
    public char getType(){return this.type;}
    public Point getPosition(){return this.position;}
    public boolean isSelected(){return this.selected;}
    public void setPosition(Point pos){this.position = pos;}
}
