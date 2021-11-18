package com.example.firsttest;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Board extends LinearLayout implements View.OnClickListener {
    private int player;
    private final Size BOARD_SIZE;
    private Tile[][] boardMatrix;
    private Tile selectedTile;
    private Tools t1;
    private Context context;
    public Board(Context context) {
        super(context);
        this.t1 = new Tools(context);
        this.context = context;
        this.setOrientation(VERTICAL);
        this.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        this.player = 1; // Player 1 starts playing first
        this.BOARD_SIZE = new Size(8,12);
        this.boardMatrix = new Tile[this.BOARD_SIZE.getHeight()][this.BOARD_SIZE.getWidth()];
        for (int i=0; i<12; i++){
            LinearLayout ly=new LinearLayout(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ly.setLayoutParams(params);
            ly.setOrientation(LinearLayout.HORIZONTAL);
            for (int j=0; j<8; j++){
                this.boardMatrix[i][j] = new Tile(context, new Point(j, i));
                this.boardMatrix[i][j].setLayoutParams(new LinearLayout.LayoutParams(getScreenWidth(context) / 8, getScreenWidth(context) / 8));
                this.boardMatrix[i][j].setOnClickListener(this::onClick);
                ly.addView(this.boardMatrix[i][j]);
            }
            this.addView(ly);
        }
        this.selectedTile = null;
        this.locateSoldiersOnBoard(this.readSoldiersFromFile("soldiers_positions", context));
        hideAllForeign(2);
    }
    private int getScreenWidth(Context context)
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();

        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return width;
    }

    @Override
    public void onClick(View view) {
        for (int i=0; i<12; i++){
            for (int j=0; j<8; j++){
                if (view == this.boardMatrix[i][j] && this.selectedTile == null){
                    Tile tile = (Tile)view;
                    if (!tile.hasSoldier()){
                        this.t1.createToast("Please choose a tile with a soldier");
                    }
                    else if (tile.getSoldier().getTeamNumber() != player)
                        this.t1.createToast("Please choose a soldier of your type");
                    else{
                        this.boardMatrix[i][j].select();
                        selectedTile = this.boardMatrix[i][j];
                    }
                }
                if (view == this.boardMatrix[i][j] && this.selectedTile != null && (Tile)view != this.selectedTile){
                    Tile tile = (Tile)view;
                    if (!tile.hasSoldier()){
                        moveSoldier(this.selectedTile, tile);
                    }
                    else if (tile.hasSoldier()){
                        if (tile.getSoldier().getTeamNumber() == player){
                            this.selectedTile.deselect();
                            tile.select();
                            this.selectedTile = tile;
                        }
                        else if (tile.getSoldier().getTeamNumber() != player){
                            Soldier soldier1 = this.selectedTile.getSoldier();
                            Soldier soldier2 = tile.getSoldier();
                            if ((Math.abs(tile.getPosition().x - this.selectedTile.getPosition().x) != 1 && Math.abs(tile.getPosition().y - this.selectedTile.getPosition().y) != 1) ||
                                    (Math.abs(tile.getPosition().x - this.selectedTile.getPosition().x) == 1 && Math.abs(tile.getPosition().y - this.selectedTile.getPosition().y) == 1)){
                                t1.createToast("Please select a different tile");
                            }
                            else{
                                int result = fight(soldier1, soldier2);
                                if (result == 0){
                                    this.selectedTile.removeSolider();
                                    tile.removeSolider();
                                }
                                if (result == soldier1.getTeamNumber()){
                                    tile.removeSolider();
                                }
                                else
                                    this.selectedTile.removeSolider();
                                if (this.player == 1){
                                    this.player = 2;
                                }
                                else
                                    this.player = 1;
                                this.selectedTile.deselect();
                                this.selectedTile = null;
                                createAlertDialog("Switch Players", "Give the phone to player number "+this.player, this.player);
                            }
                        }
                    }
                }
            }
        }
    }
    public void createAlertDialog(String title, String message, int player){
        new AlertDialog.Builder(this.context)
                .setTitle(title)
                .setMessage(message)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (player == 1){
                            hideAllForeign(2);
                            revealAllKnown(1);
                        }
                        else{
                            hideAllForeign(1);
                            revealAllKnown(2);
                        }
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public void revealAllKnown(int teamNum){
        for (int i=0; i<12; i++){
            for (int j=0; j<8; j++){
                this.boardMatrix[i][j].revealSoldier(teamNum);
            }
        }
    }
    public void hideAllForeign(int teamNum){
        for (int i=0; i<12; i++){
            for (int j=0; j<8; j++){
                this.boardMatrix[i][j].hideSoldier(teamNum);
            }
        }
    }
    public void moveSoldier(Tile fromTile, Tile newTile){
        Point oldPos = fromTile.getPosition();
        Point newPos = newTile.getPosition();
        if ((Math.abs(oldPos.x - newPos.x) != 1 && Math.abs(oldPos.y - newPos.y) != 1) ||
                (Math.abs(oldPos.x - newPos.x) == 1 && Math.abs(oldPos.y - newPos.y) == 1)){
            t1.createToast("Please select a different tile");
            return;
        }
        else{
            Soldier s1 = fromTile.getSoldier();
            fromTile.removeSolider();
            newTile.setSoldier(s1);
        }
        this.selectedTile.deselect();
        this.selectedTile = null;
        if (this.player == 1){
            this.player = 2;
        }
        else
            this.player = 1;
        createAlertDialog("Switch Players", "Give the phone to player number "+this.player, this.player);
    }
    private ArrayList<Soldier> readSoldiersFromFile(String filename, Context context){
        File f1 = new File(context.getExternalFilesDir(null).toString(), filename);
        byte[] wholeFile = new byte[(int)f1.length()];
        byte[] firstPlayer = new byte[(int)f1.length()/2];
        byte[] secondPlayer = new byte[(int)f1.length()/2];
        try {
            FileInputStream fis = new FileInputStream(f1);
            fis.read(wholeFile);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i=0; i<wholeFile.length/2; i++){
            firstPlayer[i] = wholeFile[i];
            secondPlayer[i] = wholeFile[i+wholeFile.length/2];
        }
        ArrayList<Soldier> soldiers = new ArrayList<>();
        int numOfSold1 = (int)firstPlayer[1];
        for (int i=0; i<numOfSold1; i++){
            char type = (char)firstPlayer[2+i*3];
            int x = (int)firstPlayer[4+i*3];
            int y = (int)firstPlayer[3+i*3];
            Point pos = new Point(x, y);
            soldiers.add(new Soldier(1, type, pos));
        }
        int numOfSold2 = (int)secondPlayer[1];
        for (int i=0; i<numOfSold2; i++){
            char type = (char)secondPlayer[2+i*3];
            int x = (int)secondPlayer[4+i*3];
            int y = (int)secondPlayer[3+i*3];
            Point pos = new Point(x, y);
            soldiers.add(new Soldier(2, type, pos));
        }
        return soldiers;
    }
    private void locateSoldiersOnBoard(ArrayList<Soldier> soldiers){
        for (int i=0; i<soldiers.size(); i++){
            Soldier s1 = soldiers.get(i);
            this.boardMatrix[s1.getPosition().y][s1.getPosition().x].setSoldier(s1);
        }
    }
    public int fight(Soldier soldier1, Soldier soldier2){
        if (soldier1.getType() == soldier2.getType())
            return 0;
        if (soldier1.getType() == 'A'){
            if (soldier2.getType() == 'E' || soldier2.getType() == 'F'){
                return soldier1.getTeamNumber();
            }
            return soldier2.getTeamNumber();
        }
        else if (soldier1.getType() == 'W'){
            if (soldier2.getType() == 'A' || soldier2.getType() == 'F'){
                return soldier1.getTeamNumber();
            }
            return soldier2.getTeamNumber();
        }
        else if (soldier1.getType() == 'F'){
            if (soldier2.getType() == 'E'){
                return soldier1.getTeamNumber();
            }
            return soldier2.getTeamNumber();
        }
        else if (soldier1.getType() == 'E'){
            if (soldier2.getType() == 'W'){
                return soldier1.getTeamNumber();
            }
            return soldier2.getTeamNumber();
        }
        return -1;
    }
}
