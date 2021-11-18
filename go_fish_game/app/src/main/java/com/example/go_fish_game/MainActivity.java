package com.example.go_fish_game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int playerTurn = 1;
    int gamesPlayed = 30;
    ImageView player;
    int count = 0;
    TextView roundsLeft;
    Board board;
int click=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player = findViewById(R.id.player);
        roundsLeft = findViewById(R.id.rounds_left);
        LinearLayout ly=findViewById(R.id.board);
        board = new Board(this,playerTurn);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
 ly.addView(board);
        board.setLayoutParams(params);
    }
    public void onClick(View v) {
        if (v.getId() == R.id.sunset) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Alert!!");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setMessage("pass the phone to the other player");
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (playerTurn == 1) {
                        player.setImageResource(R.drawable.fish);
                        ArrayList<Action> arr = new ActionList(MainActivity.this, playerTurn).getActionList();
                        for (Action a : arr) {
                            findViewById(a.GetId()).setVisibility(View.GONE);
                        }
                        arr = new ActionList(MainActivity.this, 2).getActionList();
                        for (Action a : arr) {
                            findViewById(a.GetId()).setVisibility(View.VISIBLE);
                        }
                        playerTurn++;
                        board.setPlayer(playerTurn);
                        board.switchTurn();
                    } else {
                        player.setImageResource(R.drawable.fisherman);
                        ArrayList<Action> arr = new ActionList(MainActivity.this, playerTurn).getActionList();
                        for (Action a : arr) {
                            findViewById(a.GetId()).setVisibility(View.GONE);
                        }
                        arr = new ActionList(MainActivity.this, 1).getActionList();
                        for (Action a : arr) {
                            findViewById(a.GetId()).setVisibility(View.VISIBLE);
                        }

                        playerTurn--;
                        board.setPlayer(playerTurn);
                        board.switchTurn();
                        gamesPlayed--;
                        roundsLeft.setText(gamesPlayed + " rounds left");
                    }
                }
            });
            alertDialogBuilder.show();
        }
        ArrayList<Action> arr = new ActionList(this, playerTurn).getActionList();
        for (Action a : arr) {
            if (a.GetId() == v.getId())
                a.select();
            else
                a.deselect();
        }


    }


        }


