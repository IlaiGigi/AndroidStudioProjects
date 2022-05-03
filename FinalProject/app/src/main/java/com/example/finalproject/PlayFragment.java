package com.example.finalproject;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PlayFragment extends Fragment {

    TextView tvCoinDisplay;
    TextView tvUsername;

    DBHelper dbHelper;

    SharedPreferences sp;

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

        tvCoinDisplay = requireView().findViewById(R.id.tvPlayCoinDisplay);
        tvUsername = requireView().findViewById(R.id.tvUsername);

        dbHelper = new DBHelper(getContext(), null, null, 1);

        sp = Utils.defineSharedPreferences(requireActivity(), "mainRoot");

        tvUsername.setText(Utils.getDataFromSharedPreferences(sp, "username", null));
        tvCoinDisplay.setText(String.valueOf(dbHelper.getUser(Utils.getDataFromSharedPreferences(sp, "username", null)).getCoins()));
    }
}