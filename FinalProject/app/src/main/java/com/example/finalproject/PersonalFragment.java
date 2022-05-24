package com.example.finalproject;

import android.content.ActivityNotFoundException;
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
import android.widget.Toast;

import java.util.Objects;


public class PersonalFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    SharedPreferences sp;

    TextView tvNameInfo;
    TextView tvSignOut;

    Button btShareGame;

    Switch switchToggleSound;

    DBHelper dbHelper;

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

        dbHelper = new DBHelper(getContext(), null, null, 1);

        switchToggleSound = requireView().findViewById(R.id.switchToggleSound);
        switchToggleSound.setOnCheckedChangeListener(this);
        switchToggleSound.setChecked(dbHelper.getUser(Utils.getDataFromSharedPreferences(sp, "username", null)).isSound());
    }

    @Override
    public void onClick(View view) {
        if (view == tvSignOut){
            sp.edit().putBoolean("rememberme", false).commit();
            startActivity(new Intent(getActivity(), HomeScreenActivity.class));
        }
        else if (view == btShareGame){
            if (dbHelper.getUser(Utils.getDataFromSharedPreferences(sp, "username", null)).getShares() == -1){
                Toast.makeText(requireActivity(),"כבר שיתפת את המשחק!", Toast.LENGTH_SHORT).show();
                return;
            }
            String textMessage = "הצטרף אליי והורד את אפליקציית 'תשחץ נא'! שם המשתמש שלי: " + Utils.getDataFromSharedPreferences(sp, "username", null);
            // Create the text message with a string.
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, textMessage);
            sendIntent.setType("text/plain");
            // Try to invoke the intent.
            try {
                startActivity(sendIntent);
                User user = dbHelper.getUser(Utils.getDataFromSharedPreferences(sp, "username", null));
                user.setShares(1);
                dbHelper.deleteUser(Utils.getDataFromSharedPreferences(sp, "username", null));
                dbHelper.insertNewUser(user);
            } catch (ActivityNotFoundException e) {
            // Define what your app should do if no activity can handle the intent.
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.getId() == R.id.switchToggleSound){
            if (switchToggleSound.isChecked()){
                User user = dbHelper.getUser(Utils.getDataFromSharedPreferences(sp, "username", null));
                user.setSound(true);
                dbHelper.deleteUser(Utils.getDataFromSharedPreferences(sp, "username", null));
                dbHelper.insertNewUser(user);
            }
            else {
                User user = dbHelper.getUser(Utils.getDataFromSharedPreferences(sp, "username", null));
                user.setSound(false);
                dbHelper.deleteUser(Utils.getDataFromSharedPreferences(sp, "username", null));
                dbHelper.insertNewUser(user);
            }
        }
    }
}