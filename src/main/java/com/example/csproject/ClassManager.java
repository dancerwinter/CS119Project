package com.example.csproject;

import org.androidannotations.annotations.EBean;

import io.realm.Realm;

@EBean
public class ClassManager {

    Realm realm;

    public ClassManager() {
        realm = Realm.getDefaultInstance();
    }

    // stuff here

    public void close() {
        realm.close();
    }
}
