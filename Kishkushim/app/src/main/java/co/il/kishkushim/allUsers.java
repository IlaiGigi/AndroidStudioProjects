package co.il.kishkushim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class allUsers extends AppCompatActivity implements View.OnClickListener
{
Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        DBhelper db = new DBhelper(this,null,null,1);
        ArrayList<User> users = db.getAllUsers();
        UserAdapter logigi = new UserAdapter(allUsers.this,users);
        ListView logigi2 = (ListView)findViewById(R.id.listview);
        logigi2.setAdapter(logigi);
        b1 = findViewById(R.id.return1);
        b1.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(this, FirstActivity1.class);
        startActivity(intent);
    }
}