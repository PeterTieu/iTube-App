package com.tieutech.itubeapp.model;

//ABOUT: Blueprint for each User object
public class User {

    //Member variables
    private byte[] userImage; //Display picture
    private String userFullName; //Full name
    private String username; //Username
    private String password; //Password
    private String phoneNumber; //Phone number

    //Constructor #1
    public User(byte[] userImage, String userFullName, String username, String password, String phoneNumber) {
        this.userImage = userImage;
        this.userFullName = userFullName;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    //Constructor #2
    public User() {
    }

    //Getters
    public byte[] getUserImage() { return userImage; }
    public String getUserFullName() { return userFullName; }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getPhoneNumber() { return phoneNumber; }

    ///Setters
    public void setUserImage(byte[] userImage) { this.userImage = userImage; }
    public void setUserFullName(String userFullName) { this.userFullName = userFullName; }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}