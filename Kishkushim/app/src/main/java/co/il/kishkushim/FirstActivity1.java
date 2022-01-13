package co.il.kishkushim;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Duration;

public class FirstActivity1 extends AppCompatActivity implements View.OnClickListener
{
    EditText username, password;
    Button register, connect, leader;
    Toast msg;
    DBhelper db = new DBhelper(this,null,null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first1);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        connect = findViewById(R.id.connect);
        leader = findViewById(R.id.leaderboard);
        leader.setOnClickListener(this::onClick);
        register.setOnClickListener(this::onClick);
        connect.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == connect.getId())
        {
            String username1 = username.getText().toString();
            User yourUser = db.getOneUser(username1);
            if (password.getText().toString().matches(yourUser.getPassword()))
            {
                Intent intent = new Intent(this, MyDets.class);
                intent.putExtra("user",username1);
                startActivity(intent);
            }
            else
            {
                msg = Toast.makeText(this, "mistaken password or username", Toast.LENGTH_SHORT);
                msg.show();
            }
        }
        else if (v.getId() == register.getId())
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else if (v.getId() == leader.getId())
        {
            Intent intent = new Intent(this, allUsers.class);
            startActivity(intent);
        }
    }
}