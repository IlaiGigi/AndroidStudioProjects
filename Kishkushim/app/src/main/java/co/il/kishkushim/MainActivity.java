package co.il.kishkushim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    DBhelper db = new DBhelper(this,null,null,1);
    EditText username1, password1, password12, age1, mail1;
    Button register1;
    Toast t1;
    RadioButton r1,r2,r3;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username1 = findViewById(R.id.new_username);
        password1 = findViewById(R.id.new_password);
        password12 = findViewById(R.id.real_password);
        age1 = findViewById(R.id.new_age);
        mail1 = findViewById(R.id.new_mail);
        register1 = findViewById(R.id.new_register);
        register1.setOnClickListener(this::onClick);
        r1 = findViewById(R.id.radio1);
        r2 = findViewById(R.id.radio2);
        r3 = findViewById(R.id.radio3);
    }

    @Override
    public void onClick(View v)
    {
        if (password1.getText().toString().matches(password12.getText().toString()))
        {
            if (!db.getOneUser(username1.getText().toString()).getUsername().matches(username1.getText().toString()))
            {
                int i=0;
                if (r1.isChecked())
                {
                    i = 1;
                }
                else if (r2.isChecked())
                {
                    i = 2;
                }
                else if (r3.isChecked())
                {
                    i=3;
                }
                User username = new User(username1.getText().toString(),password1.getText().toString() ,Integer.parseInt(age1.getText().toString()),mail1.getText().toString(),i);
                db.insertNewUser(username);
                Intent intent = new Intent(this, FirstActivity1.class);
                startActivity(intent);
            }
            else
            {
                t1 = Toast.makeText(this, "username already in use", Toast.LENGTH_SHORT);
                t1.show();
            }
        }
        else
        {
            t1 = Toast.makeText(this, "password doesnt match", Toast.LENGTH_SHORT);
            t1.show();
        }
    }
}
