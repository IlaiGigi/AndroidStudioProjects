package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavBar);

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavSelection);
        bottomNavigationView.setSelectedItemId(R.id.play);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new PlayFragment()).commit();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener bottomNavSelection = item -> {

        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.personal_area:
                fragment = new PersonalFragment();
                break;

            case R.id.achievements:
                fragment = new AchievementFragment();
                break;

            case R.id.play:
                fragment = new PlayFragment();
                break;
        }

        assert fragment != null;
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        return true;
    };

    @Override
    protected void onDestroy() {
        // If the application was destroyed, travel back to the home screen
        super.onDestroy();
        startActivity(new Intent(this, HomeScreenActivity.class));
    }
}