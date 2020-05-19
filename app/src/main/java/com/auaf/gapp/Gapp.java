package com.auaf.gapp;

import android.app.Application;

public class Gapp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        checkUpdate();
    }

    public void  checkUpdate(){

        //TODO check app latest version
    }

}
