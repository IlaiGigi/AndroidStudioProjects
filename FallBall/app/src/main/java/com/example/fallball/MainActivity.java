package com.example.fallball;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    RelativeLayout mainLayout;
    GameThread gameThread;
    Boolean executeOnResume = false;
    TextView tvAuthorizedSmileys, tvPoints;
    ImageView borderView;
    ImageView[] hearts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Removing the status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        hearts = new ImageView[3];

        mainLayout = findViewById(R.id.main_layout);
        tvAuthorizedSmileys = findViewById(R.id.authorizedSmileys);
        borderView = findViewById(R.id.border);
        tvPoints = findViewById(R.id.points);
        hearts[0] = findViewById(R.id.heart1);
        hearts[1] = findViewById(R.id.heart2);
        hearts[2] = findViewById(R.id.heart3);

        gameThread = new GameThread(mainLayout, this, borderView, tvAuthorizedSmileys, tvPoints, hearts);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameThread.pauseGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!executeOnResume){ // This prevents the program from running on resume when it launches
            executeOnResume = true;
            return;
        }
        createAlertDialog("Resume Game");
    }

    public void createAlertDialog(String title){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setCancelable(false)
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, (dialog, which) -> gameThread.resumeGame())
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onClick(View view) {
        if (view == tvAuthorizedSmileys){
            tvAuthorizedSmileys.setText(String.valueOf(gameThread.getNewRandomNumber()));
        }
    }
}