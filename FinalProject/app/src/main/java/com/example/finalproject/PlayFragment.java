package com.example.finalproject;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;


public class PlayFragment extends Fragment implements View.OnClickListener{

    TextView tvCoinDisplay;
    TextView tvUsername;
    TextView tvHeadline;
    TextView tvRegularMode;
    TextView tvKidsMode;
    TextView tvMultiplayerMode;

    ImageButton ibRegularMode;
    ImageButton ibKidsMode;
    ImageButton ibMultiplayerMode;

    DBHelper dbHelper;

    SharedPreferences sp;

    File regularModeProgress;
    File kidsModeProgress;

    public PlayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ibRegularMode = requireView().findViewById(R.id.ibRegularMode);
        ibKidsMode = requireView().findViewById(R.id.ibKidsMode);
        ibMultiplayerMode = requireView().findViewById(R.id.ibMultiplayerMode);

        tvCoinDisplay = requireView().findViewById(R.id.tvPlayCoinDisplay);
        tvUsername = requireView().findViewById(R.id.tvUsername);
        tvHeadline = requireView().findViewById(R.id.tvPlayHeadline);
        tvRegularMode = requireView().findViewById(R.id.tvRegularMode);
        tvKidsMode = requireView().findViewById(R.id.tvKidsMode);
        tvMultiplayerMode = requireView().findViewById(R.id.tvMultiplayerMode);

        ibRegularMode.setOnClickListener(this);
        ibKidsMode.setOnClickListener(this);
        ibMultiplayerMode.setOnClickListener(this);

        dbHelper = new DBHelper(getContext(), null, null, 1);

        sp = Utils.defineSharedPreferences(requireActivity(), "mainRoot");

        // Update dynamic views
        tvUsername.setText(Utils.getDataFromSharedPreferences(sp, "username", null));
        tvCoinDisplay.setText(String.valueOf(dbHelper.getUser(Utils.getDataFromSharedPreferences(sp, "username", null)).getCoins()));

        // Add underline to headline text
        SpannableString content = new SpannableString("בחירת מצב משחק");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvHeadline.setText(content);
/*
        regularModeProgress = Utils.createFile("RegularModeProgress");
        kidsModeProgress = Utils.createFile("KidsModeProgress");

        Utils.writeToFile(regularModeProgress, "1:2\n2:0\n3:0");
        Utils.writeToFile(kidsModeProgress, "1:0\n2:0\n3:0");*/
    }

    @Override
    public void onClick(View view) {
        if (view == ibRegularMode){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View promptView = layoutInflater.inflate(R.layout.kids_menu_layout, null);
            final AlertDialog alertD = new AlertDialog.Builder(getActivity()).create();

            // Set all buttons to their corresponding functions
            ImageButton ibStartLevel1 = promptView.findViewById(R.id.ibStartLevel1);
            ImageButton ibStartLevel2 = promptView.findViewById(R.id.ibStartLevel2);
            ImageButton ibStartLevel3 = promptView.findViewById(R.id.ibStartLevel3);
            ibStartLevel1.setOnClickListener(view1 -> {
                startActivity(new Intent(requireActivity(), ClassicGameActivity.class));
            });
            ibStartLevel2.setOnClickListener(view1 -> {
                // Start level 2
            });
            ibStartLevel3.setOnClickListener(view1 -> {
                // Start level 3
            });

            // String progressData = Utils.readFromFile(regularModeProgress, -1); // Fetch all data from the file
            // Set all progress bars to display the progression of each level
            ProgressBar pbLevel1 = promptView.findViewById(R.id.pbLevel1);
            TextView tvLevel1Progress = promptView.findViewById(R.id.tvLevel1Progress);

            ProgressBar pbLevel2 = promptView.findViewById(R.id.pbLevel2);
            TextView tvLevel2Progress = promptView.findViewById(R.id.tvLevel2Progress);

            ProgressBar pbLevel3 = promptView.findViewById(R.id.pbLevel3);
            TextView tvLevel3Progress = promptView.findViewById(R.id.tvLevel3Progress);

            alertD.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertD.setView(promptView);
            alertD.setCancelable(true);
            alertD.show();
            alertD.getWindow().setLayout(Utils.dpToPx(requireActivity(), 400), Utils.dpToPx(requireActivity(), 500));
        }
        else if (view == ibKidsMode){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View promptView = layoutInflater.inflate(R.layout.kids_menu_layout, null);
            final AlertDialog alertD = new AlertDialog.Builder(getActivity()).create();

            // Set all buttons to their corresponding functions
            ImageButton ibStartLevel1 = promptView.findViewById(R.id.ibStartLevel1);
            ImageButton ibStartLevel2 = promptView.findViewById(R.id.ibStartLevel2);
            ImageButton ibStartLevel3 = promptView.findViewById(R.id.ibStartLevel3);
            ibStartLevel1.setOnClickListener(view1 -> {
                // Start level 1
                Intent intent = new Intent(requireActivity(), KidsGameActivity.class);
                intent.putExtra("levelIdentifier", 1);
                sp.edit().putInt("levelIdentifier", 1).apply();
                startActivity(intent);
            });
            ibStartLevel2.setOnClickListener(view1 -> {
                // Start level 2
                Intent intent = new Intent(requireActivity(), KidsGameActivity.class);
                intent.putExtra("levelIdentifier", 2);
                sp.edit().putInt("levelIdentifier", 2).apply();
                startActivity(intent);
            });
            ibStartLevel3.setOnClickListener(view1 -> {
                // Start level 3
                Intent intent = new Intent(requireActivity(), KidsGameActivity.class);
                intent.putExtra("levelIdentifier", 3);
                sp.edit().putInt("levelIdentifier", 3).apply();
                startActivity(intent);
            });

            // String progressData = Utils.readFromFile(regularModeProgress, -1); // Fetch all data from the file
            // Set all progress bars to display the progression of each level
            ProgressBar pbLevel1 = promptView.findViewById(R.id.pbLevel1);
            TextView tvLevel1Progress = promptView.findViewById(R.id.tvLevel1Progress);

            ProgressBar pbLevel2 = promptView.findViewById(R.id.pbLevel2);
            TextView tvLevel2Progress = promptView.findViewById(R.id.tvLevel2Progress);

            ProgressBar pbLevel3 = promptView.findViewById(R.id.pbLevel3);
            TextView tvLevel3Progress = promptView.findViewById(R.id.tvLevel3Progress);

            alertD.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertD.setView(promptView);
            alertD.setCancelable(true);
            alertD.show();
            alertD.getWindow().setLayout(Utils.dpToPx(requireActivity(), 400), Utils.dpToPx(requireActivity(), 500));
        }
        else if (view == ibMultiplayerMode){

        }
    }
}