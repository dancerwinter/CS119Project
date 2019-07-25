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

    public Class getClass(String UUID){
        return realm.where(Class.class).contains("UUID", UUID).findFirst();
    }

    public RealmResults<Class> getUserClasses(String userUUID){
        RealmResults<Class> classList = realm.where(Class.class).contains("userUUID", userUUID).findAll();
        return classList.sort("intStartTime");
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

    public RealmResults<Class> getUserClassForToday(int dayInTheWeek, String userUUID){

        switch (dayInTheWeek){
            case 0:
                RealmResults<Class> classList = realm.where(Class.class).contains("userUUID",userUUID).equalTo("Sunday",true).findAll();
                return classList.sort("intStartTime");

            case 1:
                RealmResults<Class> classList2 = realm.where(Class.class).contains("userUUID",userUUID).equalTo("Monday",true).findAll();
                return classList2.sort("intStartTime");
            case 2:
                RealmResults<Class> classList3 = realm.where(Class.class).contains("userUUID",userUUID).equalTo("Tuesday",true).findAll();
                return classList3.sort("intStartTime");
            case 3:
                RealmResults<Class> classList4 = realm.where(Class.class).contains("userUUID",userUUID).equalTo("Wednesday",true).findAll();
                return classList4.sort("intStartTime");
            case 4:
                RealmResults<Class> classList5 = realm.where(Class.class).contains("userUUID",userUUID).equalTo("Thursday",true).findAll();
                return classList5.sort("intStartTime");
            case 5:
                RealmResults<Class> classList6 = realm.where(Class.class).contains("userUUID",userUUID).equalTo("Friday",true).findAll();
                return classList6.sort("intStartTime");
            case 6:
                RealmResults<Class> classList7 = realm.where(Class.class).contains("userUUID",userUUID).equalTo("Saturday",true).findAll();
                return classList7.sort("intStartTime");

        }
        return null;
    }
    // returns True if there already exists a class with the same name
    public Boolean classChecker(User u, String subject){
        RealmResults<Class> classList = getUserClasses(u.getUUID());
        for(Class c: classList){
            if(c.getSubject().equals(subject)){
                return true;
            }
        }
        return false;
    }
    public void close() {
        realm.close();
    }
}
