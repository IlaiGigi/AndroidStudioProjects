package com.example.sharedprefrencesexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class EditScreen extends AppCompatActivity implements View.OnClickListener {
    EditText etTextSize, etTextColor, etHeadlineSize, etHeadlineColor, etBackgroundColor;
    Intent intent;
    Button btnApplyChanges;
    RadioButton rbtnColor, rbtnImage;
    SharedPreferences sharedPref;
    SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_screen);
        sharedPref = getSharedPreferences("yosi",MODE_PRIVATE);
        edit = sharedPref.edit();
        etTextSize = findViewById(R.id.etChangeTextSize);
        etTextColor = findViewById(R.id.etChangeTextColor);
        etHeadlineSize = findViewById(R.id.etChangeHeadlineSize);
        etHeadlineColor = findViewById(R.id.etChangeHeadlineColor);
        etBackgroundColor = findViewById(R.id.etChangeBackgroundColor);
        btnApplyChanges = findViewById(R.id.btnApplyChanges);
        rbtnColor = findViewById(R.id.rbtnColor);
        rbtnImage = findViewById(R.id.rbtnImage);
        intent = new Intent(EditScreen.this, MainActivity.class);
        btnApplyChanges.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnApplyChanges){
            if (!etTextSize.getText().toString().equals("")){
                edit.putInt("textSize",Integer.parseInt(etTextSize.getText().toString()));
            }
            if (!etTextColor.getText().toString().equals("")){
                edit.putString("textColor", etTextColor.getText().toString());
            }
            if (!etHeadlineSize.getText().toString().equals("")){
                edit.putInt("headlineSize", Integer.parseInt(etHeadlineSize.getText().toString()));
            }
            if (!etHeadlineColor.getText().toString().equals("")){
                edit.putString("headlineColor", etHeadlineColor.getText().toString());
            }
            if (rbtnColor.isChecked() && !etBackgroundColor.getText().toString().equals("")){
                edit.putString("background", etBackgroundColor.getText().toString());
            }
            else if (rbtnImage.isChecked()){
                edit.putString("background", "image");
            }
            edit.commit();
            startActivity(intent);
        }
    }
}