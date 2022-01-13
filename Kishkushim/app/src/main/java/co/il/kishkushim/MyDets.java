package co.il.kishkushim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.controls.templates.TemperatureControlTemplate;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyDets extends AppCompatActivity implements View.OnClickListener {
TextView username2, password2, age2, mail2;
Button update2, disconnect;
User username;
ImageView img;
DBhelper db = new DBhelper(this,null,null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String name = intent.getStringExtra("user");
        setContentView(R.layout.activity_my_dets);
        username2 = findViewById(R.id.the_name);
        password2 = findViewById(R.id.the_password);
        age2 = findViewById(R.id.the_age);
        mail2 = findViewById(R.id.the_mail);
        update2 = findViewById(R.id.update_detail);
        img = findViewById(R.id.img);
        update2.setOnClickListener(this::onClick);
        disconnect = findViewById(R.id.disconnect);
        disconnect.setOnClickListener(this::onClick);
        username = db.getOneUser(name);
        username2.setText(username.getUsername());
        password2.setText(username.getPassword());
        age2.setText(username.getAge()+"");
        mail2.setText(username.getMail());
        if (username.getPicture() == 1)
        {
            img.setImageDrawable(getDrawable(R.drawable.peter));
        }
        else if (username.getPicture() == 2)
        {
            img.setImageDrawable(getDrawable(R.drawable.otis));
        }
        else if (username.getPicture() == 3)
        {
            img.setImageDrawable(getDrawable(R.drawable.turner));
        }
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == update2.getId())
        {
           Intent intent = new Intent(this, updateDets.class);
           intent.putExtra("yes",username2.getText().toString());
           startActivity(intent);
        }
        else if (v.getId() == disconnect.getId())
        {
            Intent intent = new Intent(this, FirstActivity1.class);
            startActivity(intent);
        }
    }
}
