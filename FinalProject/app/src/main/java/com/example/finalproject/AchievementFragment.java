package com.example.finalproject;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;


public class AchievementFragment extends Fragment implements View.OnClickListener {

    ImageButton ibAchievement1ClaimReward, ibAchievement2ClaimReward;

    TextView tvAchievement1RewardPercentage, tvAchievement2RewardPercentage, tvAchievementCoinDisplay;

    DBHelper dbHelper;

    SharedPreferences sp;

    RelativeLayout achievement1Layout;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");

    public AchievementFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_achievement, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        achievement1Layout = requireView().findViewById(R.id.achievement1Layout);

        tvAchievementCoinDisplay = requireView().findViewById(R.id.tvAchievementCoinDisplay);

        ibAchievement1ClaimReward = requireView().findViewById(R.id.tvAchievement1ClaimReward);
//        ibAchievement2ClaimReward = requireView().findViewById(R.id.tvAchievement2ClaimReward);

        ibAchievement1ClaimReward.setOnClickListener(this);
//        ibAchievement2ClaimReward.setOnClickListener(this);

        tvAchievement1RewardPercentage = requireView().findViewById(R.id.tvAchievement1RewardPercentage);
//        tvAchievement2RewardPercentage = requireView().findViewById(R.id.tvAchievement2RewardPercentage);

        dbHelper = new DBHelper(getContext(), null, null, 1);

        sp = Utils.defineSharedPreferences(requireActivity(), "mainRoot");

        String uuid = Utils.getDataFromSharedPreferences(sp, "UUID", null);
        Utils.getUserFromDatabase(uuid, user -> {
            tvAchievementCoinDisplay.setText(String.valueOf(user.getCoins()));
            if (Math.abs(user.getShares()) == 1) {
                ibAchievement1ClaimReward.setClickable(true);
                ibAchievement1ClaimReward.setBackgroundResource(R.drawable.green_rect);
                if (user.getShares() == -1)
                    tvAchievement1RewardPercentage.setText("הושלם");
            }
        });

    }

    @Override
    public void onClick(View view) {
        // Remember that an achievement's completion is indicated as -(num of occurrences to complete achievement) in the database
        if (view == ibAchievement1ClaimReward) {
            String uuid = Utils.getDataFromSharedPreferences(sp, "UUID", null);
            Utils.getUserFromDatabase(uuid, user -> {
                if (user.getShares() == -1) {
                    Toast.makeText(requireContext(), "כבר הושלם", Toast.LENGTH_SHORT).show();
                    return;
                }
                mDatabase.child(Utils.getDataFromSharedPreferences(sp, "UUID", null)).child("coins").setValue(user.getCoins() + 200);
                mDatabase.child(Utils.getDataFromSharedPreferences(sp, "UUID", null)).child("shares").setValue(-1);

                ValueAnimator animator = ValueAnimator.ofInt(user.getCoins(), user.getCoins() + 200);

                tvAchievement1RewardPercentage.setText("הושלם");
                if (user.isSound()) {
                    MediaPlayer mediaPlayer = MediaPlayer.create(requireContext(), R.raw.coin_sound);
                    mediaPlayer.start();
                }
                animator.setInterpolator(new LinearInterpolator());
                animator.setDuration(1500);
                animator.addUpdateListener(valueAnimator -> {
                    int val = (int) valueAnimator.getAnimatedValue();
                    tvAchievementCoinDisplay.setText(String.valueOf(val));
                });
                animator.start();
            });
        }
    }
}