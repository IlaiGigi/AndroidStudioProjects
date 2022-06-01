package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sp;

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utils.changeNotificationBarColor(this, R.color.purple_700);

        bottomNavigationView = findViewById(R.id.bottomNavBar);

        sp = Utils.defineSharedPreferences(this, "mainRoot");

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavSelection);

        Utils.insertDataToSharedPreferences(sp, "currentFragment", "1");

        bottomNavigationView.setSelectedItemId(R.id.play);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new PlayFragment()).commit();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener bottomNavSelection = item -> {

        // Each fragment gets an index between 1-3, starting from the right side of the Nav bar
        // Play - 1, Achievement - 2, Personal Info - 3

        int currentFragment = Integer.parseInt(Utils.getDataFromSharedPreferences(sp, "currentFragment", ""));

        Fragment fragment = null;
        switch (item.getItemId()){

            case R.id.personal_area:
                fragment = new PersonalFragment();
                Utils.insertDataToSharedPreferences(sp, "currentFragment", "3");
                break;

            case R.id.achievements:
                fragment = new AchievementFragment();
                Utils.insertDataToSharedPreferences(sp, "currentFragment", "2");
                break;

            case R.id.play:
                fragment = new PlayFragment();
                Utils.insertDataToSharedPreferences(sp, "currentFragment", "1");
                break;
        }

        assert fragment != null;

        // Parse the fragment to travel to
        int travelToFragment = Integer.parseInt(Utils.getDataFromSharedPreferences(sp, "currentFragment", ""));
        // Initialize the animations
        int in_anim = 0, out_anim = 0;

        // Following the algorithm stated above: assign values to the relative left fragment and the relative right one
        if (currentFragment < travelToFragment){
            in_anim = R.anim.slide_in_left;
            out_anim = R.anim.slide_out_right;
        }
        else if (currentFragment > travelToFragment){
            in_anim = R.anim.slide_in_right;
            out_anim = R.anim.slide_out_left;
        }
        else
            return true;

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .setCustomAnimations(in_anim, out_anim)
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
        return true;
    };

    @Override
    protected void onDestroy() {
        // If the application was destroyed, travel back to the home screen
        super.onDestroy();
        startActivity(new Intent(this, HomeScreenActivity.class));
    }

    @Override
    public void onBackPressed() {}
}