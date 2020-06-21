package com.auaf.gapp.application;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MyApp extends Application {

    private static MyApp myApp;



    @Override
    public void onCreate() {
        super.onCreate();


        checkUpdate();
        myApp=this;
    }


    public static MyApp getInstance(){

        return myApp;
    }




    public  void checkUpdate(){

        //Check Version App
    }




    public boolean isConnectedToInternet(){
        ConnectivityManager connectivityManager  = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null){

            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();

            if ( info !=null){
                for (int i = 0; i <info.length ; i++) {

                    if (info[i].getState()== NetworkInfo.State.CONNECTED){
                        return  true;
                    }
                }
            }
        }
        return false;
    }



}
