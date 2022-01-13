package co.il.kishkushim;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import java.util.ArrayList;

public class DBhelper extends SQLiteOpenHelper
{
    public static final String DB_NAME = "MyDB";
    public static final String USER_TABLE = "users";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String AGE = "age";
    public static final String MAIL = "mail";
    public static final String PICTURE = "picture";

    final String[] USERNAME_DETAILS = {USERNAME, PASSWORD, AGE, MAIL, PICTURE};
    public DBhelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "MyDB", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "create table "+USER_TABLE +" ("+USERNAME+ " text," +
                " "+PASSWORD+" text," +" " +AGE+" integer," +" " +MAIL+" text," + " " + PICTURE + " integer)";
        db.execSQL(createUsersTable.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
    public void insertNewUser (User user)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME, user.getUsername());
        contentValues.put(PASSWORD, user.getPassword());
        contentValues.put(AGE, user.getAge());
        contentValues.put(MAIL, user.getMail());
        contentValues.put(PICTURE,user.getPicture());
        sqLiteDatabase.insert(USER_TABLE, null, contentValues);
        sqLiteDatabase.close();
    }
    public ArrayList<User> getAllUsers()
    {
        ArrayList<User> users = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(USER_TABLE, USERNAME_DETAILS, null, null, null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast())
        {
            String username = cursor.getString((int)cursor.getColumnIndex(USERNAME));
            String password = cursor.getString((int)cursor.getColumnIndex(PASSWORD));
            int age = cursor.getInt((int)cursor.getColumnIndex(AGE));
            String mail = cursor.getString((int)cursor.getColumnIndex(MAIL));
            int picture = cursor.getInt((int)cursor.getColumnIndex(PICTURE));
            User user = new User(username, password, age, mail, picture);
            users.add(user);
            cursor.moveToNext();
        }
        sqLiteDatabase.close();
        return users;
    }
    public User getOneUser(String string)
    {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(USER_TABLE, USERNAME_DETAILS, USERNAME + "='" + string +"'", null , null, null, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast())
        {
            String password = cursor.getString((int)cursor.getColumnIndex(PASSWORD));
            int age = cursor.getInt((int)cursor.getColumnIndex(AGE));
            String mail = cursor.getString((int)cursor.getColumnIndex(MAIL));
            int picture = cursor.getInt((int)cursor.getColumnIndex(PICTURE));
            User user = new User(string,password,age,mail, picture);
            sqLiteDatabase.close();
            return user;
        }
        else
        {
            User no = new User(" "," ", 0, " ", 0);
            sqLiteDatabase.close();
            return no;
        }
    }
    public void updateUser(User user)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME, user.getUsername());
        contentValues.put(PASSWORD, user.getPassword());
        contentValues.put(AGE, user.getAge());
        contentValues.put(MAIL, user.getMail());
        contentValues.put(PICTURE,user.getPicture());
        sqLiteDatabase.update(USER_TABLE,contentValues,USERNAME + "='" + user.getUsername() +"'",null);
        sqLiteDatabase.close();
    }
}
