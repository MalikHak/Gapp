package com.auaf.gapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static  String SESSION_NAME ="Gapp_application";
    int PRIVATE_MODE=0;
    public static SessionManager instance;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private String HIGH_SCORE;

    private String IS_LOGGED_IN="IS_LOGGED_IN";



    public SessionManager(Context context) {
        preferences=context.getSharedPreferences(SESSION_NAME,PRIVATE_MODE);
        editor= preferences.edit();
    }


    public static SessionManager getInstance(Context context){

        if (instance==null){
            instance = new SessionManager(context);
        }
        return instance;
    }



 public void setHighScore(int score){
        editor.putInt(HIGH_SCORE,score);
        editor.commit();
 }


 public int getHighScore(){
        return preferences.getInt(HIGH_SCORE,0);
 }


    public void setLoggedIn(boolean login){

        editor.putBoolean(IS_LOGGED_IN,login);
        editor.commit();
    }

    public boolean getLoggedIn(){
        return preferences.getBoolean(IS_LOGGED_IN,false);
    }


}
