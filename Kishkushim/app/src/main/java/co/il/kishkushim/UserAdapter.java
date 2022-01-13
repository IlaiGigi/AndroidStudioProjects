package co.il.kishkushim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<User>
{
    public UserAdapter(Context context, ArrayList<User> users)
    {
        super(context,0,users);
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        User user = getItem(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_layout, parent, false);
        }
        TextView username = (TextView)convertView.findViewById(R.id.usernames);
        TextView password = (TextView)convertView.findViewById(R.id.passwords);
        TextView age = (TextView)convertView.findViewById(R.id.ages);
        TextView mail = (TextView)convertView.findViewById(R.id.mails);
        ImageView img = (ImageView)convertView.findViewById(R.id.imgs);
        username.setText("username: " + user.getUsername()+" ");
        password.setText("password: " + user.getPassword()+" ");
        age.setText("age: " + user.getAge()+" ");
        mail.setText("mail: " + user.getMail()+" ");
        if (user.getPicture() == 1)
        {
            img.setImageResource(R.drawable.peter);
        }
        else if (user.getPicture() == 2)
        {
            img.setImageResource(R.drawable.otis);
        }
        else if (user.getPicture() == 3)
        {
            img.setImageResource(R.drawable.turner);
        }
        return convertView;
    }
}
