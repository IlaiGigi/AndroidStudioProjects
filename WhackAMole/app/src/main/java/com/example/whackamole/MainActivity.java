package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends Activity {
    RelativeLayout mainLayout;
    public static TextView tvScore;
    ImageView heart1, heart2, heart3;
    static ImageView[] hearts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = findViewById(R.id.MainLayout);
        tvScore = findViewById(R.id.tvScore);
        heart1 = findViewById(R.id.heart1);
        heart2 = findViewById(R.id.heart2);
        heart3 = findViewById(R.id.heart3);
        hearts = new ImageView[3];
        hearts[0] = heart1;
        hearts[1] = heart2;
        hearts[2] = heart3;
    }
}