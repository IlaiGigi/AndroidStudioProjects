package com.example.send_data_mul_act;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {
    TextView tvHeadline, tvDisplayData;
    Button btSendData, btSwitchScreen;
    EditText etInputData;
    RadioGroup radioGroup;
    RelativeLayout mainLayout;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvHeadline = (TextView)findViewById(R.id.tvHeadline);
        tvDisplayData = (TextView) findViewById(R.id.tvDataDisplay);
        mainLayout = findViewById(R.id.layout1);
        tvHeadline.setText("Activity 3");
        intent = getIntent();
        String updatedText = intent.getExtras().getString("data");
        mainLayout.setBackgroundColor(getIntent().getExtras().getInt("color"));
        tvDisplayData.setText("Data: "+updatedText);
        Intent intent2 = new Intent(this, MainActivity.class);
        btSendData = (Button) findViewById(R.id.btSendData);
        etInputData = (EditText) findViewById(R.id.etData);
        btSwitchScreen = (Button)findViewById(R.id.btnSwitch);
        radioGroup = findViewById(R.id.radioGroup1);
        radioGroup.setVisibility(View.INVISIBLE);
        btSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2.putExtra("color", getIntent().getExtras().getInt("color"));
                intent2.putExtra("data", etInputData.getText().toString());
            }
        });
        btSwitchScreen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(intent2);
            }
        });
    }
}