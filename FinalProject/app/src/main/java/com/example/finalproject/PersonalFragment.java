package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Objects;


public class PersonalFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    SharedPreferences sp;

    TextView tvNameInfo;
    TextView tvSignOut;

    Button btShareGame;

    Switch switchToggleSound;

    public PersonalFragment() {
        // Required empty public constructor
    }
    // TODO: Remember to add: when logging out of the user, change sp value (key='rememberme') to unchecked.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sp = Utils.defineSharedPreferences(requireActivity(), "mainRoot");

        tvSignOut = requireView().findViewById(R.id.tvSignOut);
        tvSignOut.setOnClickListener(this);

        tvNameInfo = requireView().findViewById(R.id.tvNameInfo);
        tvNameInfo.setText(Utils.getDataFromSharedPreferences(sp, "username", null));

        btShareGame = requireView().findViewById(R.id.btShareGame);
        btShareGame.setOnClickListener(this);

        switchToggleSound = requireView().findViewById(R.id.switchToggleSound);
        switchToggleSound.setOnCheckedChangeListener(this);
        switchToggleSound.setChecked(true);
    }

    @Override
    public void onClick(View view) {
        if (view == tvSignOut){
            Utils.insertDataToSharedPreferences(sp, "rememberMe", "unchecked");
            startActivity(new Intent(getActivity(), HomeScreenActivity.class));
        }
        else if (view == btShareGame){
            // Implement game sharing using cellular messages
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.getId() == R.id.switchToggleSound){
            if (switchToggleSound.isChecked()){
                // Toggle volume on
            }
            else {
                // Toggle volume off
            }
        }
    }
}