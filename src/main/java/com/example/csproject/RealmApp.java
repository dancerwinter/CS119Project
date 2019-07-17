package com.example.csproject;

import android.app.Application;

import org.androidannotations.annotations.EApplication;

import io.realm.Realm;

@EApplication
public class RealmApp extends Application {

    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
