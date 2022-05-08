package com.tieutech.itubeapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.tieutech.itubeapp.model.User;
import com.tieutech.itubeapp.util.Util;
import java.util.ArrayList;
import java.util.List;

//ABOUT: Class that defines methods to modify the database of User objects
public class UserDatabaseHelper extends SQLiteOpenHelper {

    //Constructor
    public UserDatabaseHelper(@Nullable Context context) {
        super(context, Util.USER_DATABASE_NAME, null, Util.USER_DATABASE_VERSION);
    }

    //Create teh database with SQL commands
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE = "CREATE TABLE "
                + Util.USER_TABLE_NAME + " ("
                + Util.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + Util.USER_IMAGE + " BLOB , "
                + Util.USER_FULL_NAME + " TEXT , "
                + Util.USERNAME + " TEXT , "
                + Util.PASSWORD + " TEXT , "
                + Util.PHONE_NUMBER + " TEXT)";

        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    //2nd param: old version
    //3rd param: new version
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        String DROP_USER_TABLE = "DROP TABLE IF EXISTS ";
        sqLiteDatabase.execSQL(DROP_USER_TABLE, new String[]{Util.USER_TABLE_NAME});

        onCreate(sqLiteDatabase); //Call onCreate(..) as defined above
    }


    //Called by listener of "Save" Button in SignUpActivity
    //Function: Inserts a user to the SQLiteDatabase, then returns the rowID
    public long insertUser(User user) {

        //NOTE: get-WRITABLE-Database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //Gather all data to be inserted as a single row entry in the database
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.USER_IMAGE, user.getUserImage());
        contentValues.put(Util.USER_FULL_NAME, user.getUserFullName());
        contentValues.put(Util.USERNAME, user.getUsername());
        contentValues.put(Util.PASSWORD, user.getPassword());
        contentValues.put(Util.PHONE_NUMBER, user.getPhoneNumber());

        //Insert the data to the database and return the rowID if it is successful
        long rowId = sqLiteDatabase.insert(Util.USER_TABLE_NAME, null, contentValues);

        //Close the database
        sqLiteDatabase.close();

        //Return the row ID if insert was successful, and -1 if it failed
        return rowId;
    }

    // Called by listener of "Log In" Button in MainActivity
    // Checks to see if the user exists in the SQLiteDatabase - and returns a boolean
    public boolean fetchUser(String username, String password) {

        //NOTE: get-READABLE-Database
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(
                Util.USER_TABLE_NAME, //Table name
                new String[]{Util.USER_ID}, //Primary key column
                Util.USERNAME + "=? and "
                        + Util.PASSWORD + "=? ",
                new String[]{username, password}, //Values of the columns to match
                null, //Grouping of the rows
                null, //Filtering of the grows
                null); //Sorting of the order

        int numberOfRows = cursor.getCount(); //Get number of rows with the SAME values as username and password (most likely return just 1)

        sqLiteDatabase.close();

        if (numberOfRows > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    //Obtain the Cursor for all data in the database
    public Cursor fetchUserList() {

        //Obtain the column names
        String [] columns = new String[] {
                Util.USER_ID,
                Util.USER_IMAGE,
                Util.USER_FULL_NAME,
                Util.USERNAME,
                Util.PASSWORD,
                Util.PHONE_NUMBER};

        //NOTE: get-READABLE-Database
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(
                Util.USER_TABLE_NAME,
                columns, // The array of columns to return (pass null to get all)
                null, // The columns for the WHERE clause
                null, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                null // The sort order
        );

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    //Obtain a list of all the User objects in the database
    public List<User> fetchAllUsers() {
        List<User> userList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = " SELECT * FROM " + Util.USER_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setUserImage(cursor.getBlob(0));
                user.setUserFullName(cursor.getString(1));
                user.setUsername(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                user.setPhoneNumber(cursor.getString(4));

                userList.add(user);
            } while(cursor.moveToNext());
        }

        return userList;
    }

    //Update a Password
    public int updatePassword(User user) {
        //NOTE: get-WRITABLE-Database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.PASSWORD, user.getPassword());

        return sqLiteDatabase.update(Util.USER_DATABASE_NAME, contentValues, Util.USERNAME + "=?", new String[]{String.valueOf(user.getUsername())});
    }

    //Remove an entry from the database
    public void removeEntry(String[] entry)
    {
        SQLiteDatabase db = this.getWritableDatabase(); //Get the readable database

        //Delete the entry
        db.delete(Util.USER_DATABASE_NAME, Util.USER_IMAGE + "=? and " + Util.USER_FULL_NAME + "=? and " + Util.USERNAME + "=? and " + Util.PASSWORD + "=? and " + Util.PHONE_NUMBER + "=?", entry);

        db.close();
    }
}