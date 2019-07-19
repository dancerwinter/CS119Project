package com.example.csproject;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

@EBean
public class ClassManager {

    Realm realm;

    public ClassManager() {
        realm = Realm.getDefaultInstance();
    }

    // stuff here
    public Class createClass(String subject, String section, String building, String room, String teacher, String timeStart, String timeEnd , String userUUID, Boolean Monday, Boolean Tuesday, Boolean Wednesday, Boolean Thursday, Boolean Friday, Boolean Saturday, Boolean Sunday ){
        Class c = new Class();
        c.setUUID(UUID.randomUUID().toString());
        c.setUserUUID(userUUID);
        c.setSubject(subject);
        c.setSection(section);
        c.setBuilding(building);
        c.setRoom(room);
        c.setTeacher(teacher);
        c.setTimeStart(timeStart);
        c.setTimeEnd(timeEnd);
        c.setMonday(Monday);
        c.setTuesday(Tuesday);
        c.setWednesday(Wednesday);
        c.setThursday(Thursday);
        c.setFriday(Friday);
        c.setSaturday(Saturday);
        c.setSunday(Sunday);
        return c;
    }


    public RealmResults<Class> getAllClasses(){
        return realm.where(Class.class).findAll();
    }

    public RealmResults<Class> getUserClasses(String userUUID){
        return realm.where(Class.class).contains("userUUID", userUUID).findAll();
    }
    public void addClass(Class c){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(c);
        realm.commitTransaction();
    }

    public void deleteClass(Class c){
        realm.beginTransaction();
        try {
            c.deleteFromRealm();
        } catch (Exception e) {
            e.printStackTrace();
        }
        realm.commitTransaction();
    }
    public void close() {
        realm.close();
    }
}
