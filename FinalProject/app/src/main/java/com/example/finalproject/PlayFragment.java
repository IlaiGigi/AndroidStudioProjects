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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;


public class PlayFragment extends Fragment implements View.OnClickListener{

    TextView tvCoinDisplay;
    TextView tvUsername;
    TextView tvHeadline;
    TextView tvRegularMode;
    TextView tvKidsMode;

    ImageButton ibRegularMode;
    ImageButton ibKidsMode;
    ImageButton ibMultiplayerMode;

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

        tvCoinDisplay = requireView().findViewById(R.id.tvPlayCoinDisplay);
        tvUsername = requireView().findViewById(R.id.tvUsername);
        tvHeadline = requireView().findViewById(R.id.tvPlayHeadline);
        tvRegularMode = requireView().findViewById(R.id.tvRegularMode);
        tvKidsMode = requireView().findViewById(R.id.tvKidsMode);

        ibRegularMode.setOnClickListener(this);
        ibKidsMode.setOnClickListener(this);

        sp = Utils.defineSharedPreferences(requireActivity(), "mainRoot");

        // Update dynamic views
        tvUsername.setText(Utils.getDataFromSharedPreferences(sp, "username", null));

        String uuid = Utils.getDataFromSharedPreferences(sp, "UUID", null);
        Utils.getUserFromDatabase(uuid, user -> tvCoinDisplay.setText(String.valueOf(user.getCoins())));

        // Add underline to headline text
        SpannableString content = new SpannableString("?????????? ?????? ????????");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvHeadline.setText(content);
    }

    @Override
    public void onClick(View view) {
        if (view == ibRegularMode){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View promptView = layoutInflater.inflate(R.layout.classic_menu_layout, null);
            final AlertDialog alertD = new AlertDialog.Builder(getActivity()).create();

            // Set all buttons to their corresponding functions
            ImageButton ibStartLevel1 = promptView.findViewById(R.id.ibStartLevel1);
            ImageButton ibStartLevel2 = promptView.findViewById(R.id.ibStartLevel2);
            ImageButton ibStartLevel3 = promptView.findViewById(R.id.ibStartLevel3);

            Intent intent = new Intent(getActivity(), ClassicGameActivity.class);

            ibStartLevel1.setOnClickListener(view1 -> {
                intent.putExtra("levelIdentifier", 1);
                startActivity(intent);
            });

            ibStartLevel2.setOnClickListener(view1 -> {
                intent.putExtra("levelIdentifier", 2);
                startActivity(intent);
            });

            ibStartLevel3.setOnClickListener(view1 -> {
                intent.putExtra("levelIdentifier", 3);
                startActivity(intent);
            });

            // String progressData = Utils.readFromFile(regularModeProgress, -1); // Fetch all data from the file
            // Set all progress bars to display the progression of each level

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