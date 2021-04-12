package com.example.cinemaera;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;

public class Util {
    public static String FAVOURITE_TOKEN = null;
    public static String SESSION_KEY = "";
    public static String SESSION_USERID = "";
    public static String SESSION_NAME = "";
    public static String SESSION_EMAIL = "";

    public static boolean SetKey(Context context, String key, String value){
        SharedPreferences.Editor editor = context.getSharedPreferences("Cinema_pref", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String GetValue(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Cinema_pref", MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static void ClearAllSharedPreference(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Cinema_pref", MODE_PRIVATE);
        sharedPreferences.edit().remove("Cinemapref_session").apply();
        sharedPreferences.edit().remove("Cinemapref_uid").apply();
        sharedPreferences.edit().remove("Cinemapref_email").apply();
        sharedPreferences.edit().remove("Cinemapref_name").apply();
        sharedPreferences.edit().remove("Cinemapref_favourite_token").apply();
    }
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public  Util(Context context){
        sharedPreferences = context.getSharedPreferences("appkey",0);
        editor = sharedPreferences.edit();
        editor.commit();
    }
    public void setlogin(boolean login){
        editor.putBoolean("key_login",login);
        editor.commit();
    }
    public boolean getlogin(){
        return sharedPreferences.getBoolean("key_login",false);
    }
    public void setusername(String username){
        editor.putString("key_username",username);
        editor.commit();
    }
    public String getusername(){
        return sharedPreferences.getString("key_username","");
    }

    public static void GenerateFavouriteToken(Context context){
        FAVOURITE_TOKEN = UUID.randomUUID().toString();
        SetKey(context, "Cinemapref_favourite_token", FAVOURITE_TOKEN);
    }

    public static void ClearCurrentCartToken(Context context){
        FAVOURITE_TOKEN = null;
        SetKey(context, "Cinemapref_favourite_token", null);
    }

}

