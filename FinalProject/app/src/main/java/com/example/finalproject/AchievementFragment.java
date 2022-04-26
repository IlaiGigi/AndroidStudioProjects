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

    ImageButton ibAchievement1ClaimReward;

    TextView tvAchievement1RewardPercentage, tvAchievementCoinDisplay;

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

        ibAchievement1ClaimReward.setOnClickListener(this);

        tvAchievement1RewardPercentage = requireView().findViewById(R.id.tvAchievement1RewardPercentage);

        dbHelper = new DBHelper(getContext(), null, null, 1);

        sp = Utils.defineSharedPreferences(requireActivity(), "mainRoot");

        User currentUser = dbHelper.getUser(Utils.getDataFromSharedPreferences(sp, "username", null));

        int shareProgression = currentUser.getShares();

        tvAchievementCoinDisplay.setText(String.valueOf(currentUser.getCoins()));

        tvAchievement1RewardPercentage.setText(Math.round(100* (shareProgression / Integer.parseInt(tvAchievement1RewardPercentage.getTag().toString()))) + "%");

        if (Math.round(100* (shareProgression / Integer.parseInt(tvAchievement1RewardPercentage.getTag().toString()))) == 100){
            ibAchievement1ClaimReward.setClickable(true);
            ibAchievement1ClaimReward.setBackgroundResource(R.drawable.green_rect);
            Log.d("yosi", "invoked");
        }
    }

    @Override
    public void onClick(View view) {
        if (view == ibAchievement1ClaimReward){
            if (achievement1Layout.getTag().equals("claimed"))
                return;
            User user = dbHelper.getUser(Utils.getDataFromSharedPreferences(sp, "username", null));
            ValueAnimator animator = ValueAnimator.ofInt(user.getCoins(), user.getCoins() + 200);
            user.setCoins(user.getCoins() + 200);
            dbHelper.deleteUser(Utils.getDataFromSharedPreferences(sp, "username", null));
            dbHelper.insertNewUser(user);
            tvAchievement1RewardPercentage.setText("הושלם");
            achievement1Layout.setTag("claimed");
            MediaPlayer mediaPlayer = MediaPlayer.create(requireContext(), R.raw.coin_sound);
            mediaPlayer.start();
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