package com.example.testsimulation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView ivTime, ivPlayer, ivAction1, ivAction2, ivAction3, ivAction4;
    ImageView[] ivActions;
    TextView tvRoundCounter;
    String player;
    int turns = 30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivTime = findViewById(R.id.ivTime);
        ivPlayer = findViewById(R.id.ivPlayer);
        tvRoundCounter = findViewById(R.id.tvRoundCounter);
        ivAction1 = findViewById(R.id.ivAction1);
        ivAction2 = findViewById(R.id.ivAction2);
        ivAction3 = findViewById(R.id.ivAction3);
        ivAction4 = findViewById(R.id.ivAction4);
        ivTime.setOnClickListener(this);
        ivAction1.setOnClickListener(this);
        ivAction2.setOnClickListener(this);
        ivAction3.setOnClickListener(this);
        ivAction4.setOnClickListener(this);
        player = "fisherman";
    }

    @Override
    public void onClick(View view) {
        if (view == ivTime) {
            if (player.equals("fisherman")) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Switch Player")
                        .setMessage("Hand the phone over to the fish")
                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert).show();
                ivPlayer.setImageResource(R.drawable.fish);
                player = "fish";
                ivAction1.setImageResource(R.drawable.swim);
                ivAction2.setImageResource(R.drawable.attak);
                ivAction3.setVisibility(View.INVISIBLE);
                ivAction4.setVisibility(View.INVISIBLE);
            }
            else if (player.equals("fish")) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Switch Player")
                        .setMessage("Hand the phone over to the fisherman")
                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert).show();
                ivPlayer.setImageResource(R.drawable.fisherman);
                player = "fisherman";
                ivAction1.setImageResource(R.drawable.paddles);
                ivAction2.setImageResource(R.drawable.fishing_rod);
                ivAction3.setVisibility(View.VISIBLE);
                ivAction4.setVisibility(View.VISIBLE);
            }
            turns--;
            tvRoundCounter.setText(turns+"");
        }
    }
}