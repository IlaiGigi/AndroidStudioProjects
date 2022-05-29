package com.example.finalproject;

import android.animation.ValueAnimator;
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


public class AchievementFragment extends Fragment implements View.OnClickListener{

    ImageButton ibAchievement1ClaimReward, ibAchievement2ClaimReward;

    TextView tvAchievement1RewardPercentage, tvAchievement2RewardPercentage, tvAchievementCoinDisplay;

    DBHelper dbHelper;

    SharedPreferences sp;

    RelativeLayout achievement1Layout;

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

        User currentUser = dbHelper.getUser(Utils.getDataFromSharedPreferences(sp, "username", null));

        int shareProgression = currentUser.getShares();

        tvAchievementCoinDisplay.setText(String.valueOf(currentUser.getCoins()));

        if (Math.round(Math.abs(100* (shareProgression / Integer.parseInt(tvAchievement1RewardPercentage.getTag().toString())))) == 100){
            ibAchievement1ClaimReward.setClickable(true);
            ibAchievement1ClaimReward.setBackgroundResource(R.drawable.green_rect);
            if (dbHelper.getUser(Utils.getDataFromSharedPreferences(sp, "username", null)).getShares() == -1)
                tvAchievement1RewardPercentage.setText("הושלם");
        }
    }

    @Override
    public void onClick(View view) {
        // Remember that an achievement's completion is indicated as -(num of occurrences to complete achievement) in the database
        if (view == ibAchievement1ClaimReward){
            if (dbHelper.getUser(Utils.getDataFromSharedPreferences(sp, "username", null)).getShares() == -1){
                Toast.makeText(requireContext(), "כבר הושלם", Toast.LENGTH_SHORT).show();
                return;
            }
            User user = dbHelper.getUser(Utils.getDataFromSharedPreferences(sp, "username", null));
            ValueAnimator animator = ValueAnimator.ofInt(user.getCoins(), user.getCoins() + 200);
            user.setCoins(user.getCoins() + 200);
            user.setShares(-1);
            dbHelper.deleteUser(Utils.getDataFromSharedPreferences(sp, "username", null));
            dbHelper.insertNewUser(user);
            tvAchievement1RewardPercentage.setText("הושלם");
            if (dbHelper.getUser(Utils.getDataFromSharedPreferences(sp, "username", null)).isSound()){
                MediaPlayer mediaPlayer = MediaPlayer.create(requireContext(), R.raw.coin_sound);
                mediaPlayer.start();
            }
            animator.setInterpolator(new LinearInterpolator());
            animator.setDuration(1500);
            animator.addUpdateListener(valueAnimator -> {
                int val = (int)valueAnimator.getAnimatedValue();
                tvAchievementCoinDisplay.setText(String.valueOf(val));
            });
            animator.start();
        }
    }
}