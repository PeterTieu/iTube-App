package com.tieutech.itubeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.tieutech.itubeapp.data.YouTubeURLDatabaseHelper;
import com.tieutech.itubeapp.model.YouTubeURL;
import com.tieutech.itubeapp.util.Util;

//ABOUT: Activity where the user could:
// 1: Enter YouTube URLs
// 2: Play the playlist
// 3: Add the current YouTube URL to the playlist
// 4: View the playlist
public class HomeActivity extends AppCompatActivity {

    //View variable
    EditText ytURLEditText;

    //Database variable
    YouTubeURLDatabaseHelper youTubeURLDatabaseHelper = new YouTubeURLDatabaseHelper(this);

    //Data variables
    String ytURL;
    YouTubeURL youTubeURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Obtain view
        ytURLEditText = (EditText) findViewById(R.id.ytURLEditText);
    }

    //Listener for "Play" Button
    public void playClick(View view) {

        ytURL = ytURLEditText.getText().toString(); //Obtain the entered YouTube URL

        //Start the PlayActivity
        Intent playIntent = new Intent(HomeActivity.this, PlayActivity.class);
        startActivity(playIntent);
    }

    //Listener for the "Add to Playlist" Button
    public void addToPlaylistClick(View view) {

        ytURL = ytURLEditText.getText().toString(); //Obtain the entered YouTube URL
        youTubeURL = new YouTubeURL(ytURL); //Instantiate a youTubeURL object with the entered YouTube URL

        long rowID = youTubeURLDatabaseHelper.insertYouTubeURL(youTubeURL); //Insert the youTubeURL to the database

        //If a Row ID exists, i.e. the data has been added to the SQLiteDatabase
        if (rowID > 0) {
            Util.makeToast(getApplicationContext(), "Inserted YouTube URL successfully!");
        }
        //If a Row ID DOES NOT exist, i.e. the data was not added to the SQLiteDatabase
        else {
            Util.makeToast(getApplicationContext(), "YouTube URL insert error!");
        }
    }

    //Listener for "My Playlist" Button
    public void myPlaylistClick(View view) {
        Intent myPlaylistIntent = new Intent(HomeActivity.this, MyPlaylistActivity.class);
        startActivity(myPlaylistIntent);
    }
}
