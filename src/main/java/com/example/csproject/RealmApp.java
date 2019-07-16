package com.example.csproject;

import android.app.Application;

import io.realm.Realm;

public class RealmApp extends Application {

    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
