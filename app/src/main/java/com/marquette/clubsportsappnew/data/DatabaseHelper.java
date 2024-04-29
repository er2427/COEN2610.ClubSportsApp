package com.marquette.clubsportsappnew.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "USERS.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "USERS.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table allusers(username TEXT primary key, password TEXT, firstName TEXT, lastName TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists allusers");
    }


    public Boolean insertData(String username, String password, String firstName, String lastName){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("firstName", firstName);
        contentValues.put("lastName", lastName);
        long results = MyDB.insert("allusers", null, contentValues);
        if(results == -1 ) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkUsername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from allusers where username = ?", new String[]{username});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from allusers where username = ? and password = ?", new String[] {username, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Updates the current password in the database with the provided new password.
     *
     * @param username The username of the user.
     * @param newPassword The new password to set.
     */
    public void updatePassword(String username, String newPassword) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", newPassword);
        MyDB.update("allusers", contentValues, "username = ?", new String[]{username});
    }


    public Cursor getUserData(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from allusers where username = ?", new String[]{username});
        return cursor;
    }

}
