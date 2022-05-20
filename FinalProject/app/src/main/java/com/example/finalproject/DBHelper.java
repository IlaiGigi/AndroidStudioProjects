package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.finalproject.User;

public class DBHelper extends SQLiteOpenHelper {
    final String DB_NAME = "UsersDatabase.db";

    final String USER_TABLE = "users";
    final String USER_USERNAMES = "usernames";
    final String USER_PASSWORDS = "passwords";
    final String USER_COINS = "coins";
    final String USER_SHARES = "shares";
    final String USER_SOUND = "sound";
    final String[] USER_COLUMNS = {USER_USERNAMES, USER_PASSWORDS, USER_COINS, USER_SHARES, USER_SOUND};

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "MyDB3.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createUsersTableQuery =
                "create table " + USER_TABLE +
                        " (" + USER_USERNAMES + " text," +
                        " " + USER_PASSWORDS + " text," +
                        " " + USER_COINS + " text," +
                        " " + USER_SHARES + " text," +
                        " " + USER_SOUND + " number)";
        Log.d("DBTEST", createUsersTableQuery);
        sqLiteDatabase.execSQL(createUsersTableQuery.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void insertNewUser(User user){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_USERNAMES, user.getUsername());
        contentValues.put(USER_PASSWORDS, user.getPassword());
        contentValues.put(USER_COINS, user.getCoins());
        contentValues.put(USER_SHARES, user.getShares());
        int state = ((user.isSound()) ? 1 : 0);
        contentValues.put(USER_SOUND, state);
        sqLiteDatabase.insert(USER_TABLE, null, contentValues);
    }
    public User getUser(String userName){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String condition = USER_USERNAMES + "='" + userName+"'";
        Cursor cursor = sqLiteDatabase.query(USER_TABLE, USER_COLUMNS, condition, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.isAfterLast()){
            return null;
        }
        String username = cursor.getString((int)cursor.getColumnIndex(USER_USERNAMES));
        String password = cursor.getString((int)cursor.getColumnIndex(USER_PASSWORDS));
        int coins = cursor.getInt((int)cursor.getColumnIndex(USER_COINS));
        int shares = cursor.getInt((int)cursor.getColumnIndex(USER_SHARES));
        boolean sound = cursor.getInt((int)cursor.getColumnIndex(USER_SOUND)) == 1;
        return new User(username, password, coins, shares, sound);
    }
    public void deleteUser(String username){
        String where = USER_USERNAMES + "='" + username+"'";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(USER_TABLE, where, null);
    }
}