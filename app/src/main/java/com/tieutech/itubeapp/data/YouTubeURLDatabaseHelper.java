package com.tieutech.itubeapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.tieutech.itubeapp.model.YouTubeURL;
import com.tieutech.itubeapp.util.Util;
import java.util.ArrayList;
import java.util.List;

//ABOUT: Class that defines methods to modify the database of YouTubeURL objects
public class YouTubeURLDatabaseHelper extends SQLiteOpenHelper {

    //Constructor
    public YouTubeURLDatabaseHelper(@Nullable Context context) {
        super(context, Util.YOUTUBE_URL_DATABASE_NAME, null, Util.YOUTUBE_URL_DATABASE_VERSION);
    }

    //Create the database with SQL commands
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_YOUTUBE_URL_TABLE = "CREATE TABLE "
                + Util.YOUTUBE_URL_TABLE_NAME + " ("
                + Util.YOUTUBE_URL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + Util.YOUTUBE_URL + " TEXT)";

        sqLiteDatabase.execSQL(CREATE_YOUTUBE_URL_TABLE);
    }

    //2nd param: old version
    //3rd param: new version
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        String DROP_USER_TABLE = "DROP TABLE IF EXISTS ";
        sqLiteDatabase.execSQL(DROP_USER_TABLE, new String[]{Util.YOUTUBE_URL_TABLE_NAME});

        onCreate(sqLiteDatabase); //Call onCreate(..) as defined above
    }

    //Called by listener of the "Add to Playlist" Button of HomeActivity
    //Function: Inserts a user to the SQLiteDatabase, then returns the rowID
    public long insertYouTubeURL(YouTubeURL youTubeURL) {

        //NOTE: get-WRITABLE-Database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //Gather all data to be inserted as a single row entry in the database
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.YOUTUBE_URL, youTubeURL.getYtURL());

        //Insert the data to the database and return the rowID if it is successful
        long rowId = sqLiteDatabase.insert(Util.YOUTUBE_URL_TABLE_NAME, null, contentValues);

        //Close the database
        sqLiteDatabase.close();

        //Return the row ID if insert was successful, and -1 if it failed
        return rowId;
    }

    // Check to see if the specified YouTubeURL String exists in the SQLiteDatabase - and returns a boolean
    public boolean fetchYouTubeURL(String ytURL) {

        //NOTE: get-READABLE-Database
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(
                Util.YOUTUBE_URL_TABLE_NAME, //Table name
                new String[]{Util.YOUTUBE_URL_ID}, //Primary key column
                Util.YOUTUBE_URL + "=?",
                new String[]{ytURL}, //Values of the columns to match
                null, //Grouping of the rows
                null, //Filtering of the grows
                null); //Sorting of the order

        int numberOfRows = cursor.getCount();

        sqLiteDatabase.close();

        if (numberOfRows > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    //Obtain the Cursor for all data in the database
    public Cursor fetchYouTubeURLList() {

        //Obtain the column names
        String [] columns = new String[] {
                Util.YOUTUBE_URL};

        //NOTE: get-READABLE-Database
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(
                Util.YOUTUBE_URL_TABLE_NAME,
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

    //Obtain a list of all the YouTubeURL objects in the database
    public List<YouTubeURL> fetchAllYouTubeURLs() {
        List<YouTubeURL> youTubeURLList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = " SELECT * FROM " + Util.YOUTUBE_URL_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                YouTubeURL youTubeURL = new YouTubeURL();
                youTubeURL.setYtURL(cursor.getString(1));

                youTubeURLList.add(youTubeURL);
            } while(cursor.moveToNext());
        }

        return youTubeURLList;
    }

    //Remove an entry from the database
    public void removeEntry(String[] entry)
    {
        SQLiteDatabase db = this.getWritableDatabase(); //Get the readable database

        //Delete the entry
        db.delete(Util.YOUTUBE_URL_TABLE_NAME, Util.YOUTUBE_URL + "=?", entry);

        db.close();
    }
}
