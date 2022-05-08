package com.tieutech.itubeapp.model;

//ABOUT: Blueprint for each YouTubeURL object
public class YouTubeURL {

    //Member variable
    String ytURL; //The YouTubeURL String

    //Constructor #1
    public YouTubeURL(){}

    //Constructor #2
    public YouTubeURL(String ytURL) {
        this.ytURL = ytURL;
    }

    //Getter
    public String getYtURL() { return ytURL; }

    //Setter
    public void setYtURL(String ytURL) { this.ytURL = ytURL; }
}
