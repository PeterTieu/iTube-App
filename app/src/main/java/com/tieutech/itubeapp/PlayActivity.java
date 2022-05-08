package com.tieutech.itubeapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.tieutech.itubeapp.config.YouTubeConfig;
import com.tieutech.itubeapp.data.YouTubeURLDatabaseHelper;
import com.tieutech.itubeapp.model.YouTubeURL;
import com.tieutech.itubeapp.util.Util;
import java.util.ArrayList;
import java.util.List;

//ABOUT: Activity that plays YouTube videos based on the list of YouTube URLs (that can be viewed in My Playlist)
public class PlayActivity extends YouTubeBaseActivity {

    //View variables
    YouTubePlayerView ytPlayer;
    Button ytPlayButton;

    //Listener variables
    YouTubePlayer.OnInitializedListener mOnInitializedListener;

    //List variables
    List<YouTubeURL> youTubeURLList = new ArrayList<>();
    List<String> youTubeURLStringList = new ArrayList<>();
    List<String> youTubeURLPlayableStringList = new ArrayList<>();

    //Database variable
    YouTubeURLDatabaseHelper youTubeURLDatabaseHelper = new YouTubeURLDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        //Obtain view variable
        ytPlayer = (YouTubePlayerView) findViewById(R.id.ytPlayer);

        //Obtain list of YouTubeURL objects from the YoutubeURL database
        youTubeURLList = youTubeURLDatabaseHelper.fetchAllYouTubeURLs();

        //Extract list of YouTubeURL Strings from the list of YouTubeURL objects
        for (YouTubeURL youTubeURL : youTubeURLList) {
            youTubeURLStringList.add(youTubeURL.getYtURL());
        }

        //Extract list of YouTube URL substrings fro the list of YouTubeURL Strings
        //NOTE: These String elements are necessary as they can be recognised by the loadVideos(..) method of the YouTubePlayer class (below)
        for (String youTubeURLPlayableString : youTubeURLStringList) {
            youTubeURLPlayableStringList.add(youTubeURLPlayableString.substring(youTubeURLPlayableString.lastIndexOf("v=") + 2));
        }

        //Delay the initialisation of the YouTubePlayer by 100ms
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ytPlayer.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);
            }
        }, 100);


        //Listener for the YouTubePlayer
        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {

            //Listener for what happens when the YouTubePlayer is initialised
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                Log.i("PlayActivity", "Success initializing");

                //If the youTubeURLStringList is null, contains whitespace elements, or is empty
                if (youTubeURLStringList.contains(null) || youTubeURLStringList.contains("") || youTubeURLStringList.isEmpty()) {
                    Util.makeToast(getApplicationContext(), "No videos in playlist to play!"); //Display Toast to notify user there is no YouTube URL to play any videos for
                }
                else {
                    youTubePlayer.loadVideos(youTubeURLPlayableStringList); //Play YouTube videos based on the list
                }
            }

            //Listener for what happens when the YouTubePlayer is cannot be initialised
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.i("PlayActivity", "error initialising");
            }
        };
    }
}