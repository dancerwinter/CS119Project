package com.example.csproject;

import org.androidannotations.annotations.EBean;

import io.realm.Realm;

@EBean
public class UserManager {

    Realm realm;

    public UserManager() {
        realm = Realm.getDefaultInstance();
    }

    // stuff here

    public void close() {
        realm.close();
    }
}
