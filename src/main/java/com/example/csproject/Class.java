package com.example.csproject;

import io.realm.RealmObject;

public class Class extends RealmObject {

    private String user; // maybe be the foreign key in this situation
    private String name;
    private String section;
    private String teacher;
    private String building;
    private String room;
    private String repeat; // M W F
    private String timeStart;
    private String timeEnd;

}
