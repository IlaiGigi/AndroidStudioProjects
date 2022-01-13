package co.il.kishkushim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class updateDets extends AppCompatActivity implements View.OnClickListener
{
    EditText username3, password3, password32, age3, mail3;
    Button update3;
    DBhelper db = new DBhelper(this,null,null,1);
    String s;
    Toast t1;
    RadioButton r1, r2, r3;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_dets);
        username3 = findViewById(R.id.updated_username);
        password3 = findViewById(R.id.updated_password);
        password32 = findViewById(R.id.updated_new_password);
        age3 = findViewById(R.id.updated_age);
        mail3 = findViewById(R.id.updated_mail);
        update3 = findViewById(R.id.newUpdate);
        r1 = findViewById(R.id.radio11);
        r2 = findViewById(R.id.radio22);
        r3 = findViewById(R.id.radio33);
        update3.setOnClickListener(this::onClick);
        Intent intent = getIntent();
        s = intent.getStringExtra("yes");
        User u1;
        u1 = db.getOneUser(s);
        username3.setText(u1.getUsername());
        password3.setText(u1.getPassword());
        password32.setText(u1.getPassword());
        age3.setText(u1.getAge()+"");
        mail3.setText(u1.getMail());
        if (u1.getPicture() == 1)
        {
            r1.setChecked(true);
        }
        else if (u1.getPicture() == 2)
        {
            r1.setChecked(true);
        }
        else if (u1.getPicture() == 3)
        {
            r1.setChecked(true);
        }

    }

    @Override
    public void onClick(View v)
    {
        if (password32.getText().toString().matches(password3.getText().toString()))
        {
            if (!db.getOneUser(username3.getText().toString()).getUsername().matches(username3.getText().toString()) || username3.getText().toString().matches(s))
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
                User user = new User(username3.getText().toString(),password3.getText().toString(),Integer.parseInt(age3.getText().toString()),mail3.getText().toString(),i);
                db.updateUser(user);
                Intent intent = new Intent(this, MyDets.class);
                intent.putExtra("user", username3.getText().toString());
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