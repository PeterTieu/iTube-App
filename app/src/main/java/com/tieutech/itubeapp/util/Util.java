package com.tieutech.itubeapp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;

//ABOUT: Utility class for the project
public class Util {
    //Shared Preferences
    public static final String SHARED_PREF_DATA = "shared_pref_data";
    public static final String SHARED_PREF_ACTIVE_USERNAME = "shared_pref_active_username";

    //User Database
    public static final int USER_DATABASE_VERSION = 1;
    public static final String USER_DATABASE_NAME = "user_db";
    public static final String USER_TABLE_NAME = "users_table";
    public static final String USER_IMAGE = "user_image";
    public static final String USER_ID = "user_id";
    public static final String USER_FULL_NAME = "user_full_name";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String PHONE_NUMBER = "phone_number";

    //YouTubeURL Database
    public static final int YOUTUBE_URL_DATABASE_VERSION = 1;
    public static final String YOUTUBE_URL_DATABASE_NAME = "youtube_url_db";
    public static final String YOUTUBE_URL_TABLE_NAME = "youtube_url_table";
    public static final String YOUTUBE_URL_ID = "youtube_url_id";
    public static final String YOUTUBE_URL = "youtube_url";

    //Convert from Bitmap to byte array
    public static byte[] getBytesArrayFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    //Make a Toast
    public static void makeToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
