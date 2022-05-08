package com.tieutech.itubeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.tieutech.itubeapp.adapter.MyPlaylistRecyclerViewAdapter;
import com.tieutech.itubeapp.data.YouTubeURLDatabaseHelper;
import com.tieutech.itubeapp.model.YouTubeURL;
import com.tieutech.itubeapp.util.Util;
import java.util.ArrayList;
import java.util.List;

//ABOUT: Activity showing a list of all the YouTube URLs added to the Playlist
public class MyPlaylistActivity extends AppCompatActivity implements MyPlaylistRecyclerViewAdapter.OnMyPlaylistListener{

    //View variables
    RecyclerView myPlaylistRecyclerView;
    MyPlaylistRecyclerViewAdapter myPlaylistRecyclerViewAdapter;

    //List variables
    List<YouTubeURL> youTubeURLList = new ArrayList<>();

    //Database variable
    YouTubeURLDatabaseHelper youTubeURLDatabaseHelper = new YouTubeURLDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_playlist);

        //Obtain view
        myPlaylistRecyclerView = (RecyclerView) findViewById(R.id.myPlaylistRecyclerView);

        youTubeURLList = youTubeURLDatabaseHelper.fetchAllYouTubeURLs(); //Obtain a list of all the YouTubeURL objects added to the playlist

        //RecyclerViewAdapter to link the RecyclerView for Orders to the data
        myPlaylistRecyclerViewAdapter = new MyPlaylistRecyclerViewAdapter(youTubeURLList, this, this); //Instantiate the Recyclerview Adapter
        myPlaylistRecyclerView.setAdapter(myPlaylistRecyclerViewAdapter); //Set the Adapter to the RecyclerView

        //LinearLayoutManager to set the layout of the RecyclerView (and make it horizontal)
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        myPlaylistRecyclerView.setLayoutManager(layoutManager); //Link the LayoutManager to the RecyclerView

    }

    //Listener for the selection of a "Remove" icon
    @Override
    public void onRemoveClick(int position) {

        String[] entry = new String[]{youTubeURLList.get(position).getYtURL()}; //Entry to be removed
        youTubeURLDatabaseHelper.removeEntry(entry); //Remove the entry
        Util.makeToast(this, "YouTube URL removed!"); //Display Toast

        refreshActivity(); //Refresh the activity
    }

    //Refresh the activity
    public void refreshActivity() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}