package com.example.csproject;

import org.androidannotations.annotations.EBean;

import io.realm.Realm;
import io.realm.RealmResults;

@EBean
public class HomeworkManager {
    private Realm realm;
    public HomeworkManager(){
        this.realm = Realm.getDefaultInstance();
    }

    public void addHomework(Homework h){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(h);
        realm.commitTransaction();
    }

    public Homework createHomework(String title, String description, String username, String subject){
        Homework h = new Homework();
        h.setTitle(title);
        h.setDescription(description);
        h.setOwnerUsername(username);
        h.setSubject(subject);
        return h;
    }
    public RealmResults<Homework> getHomeworkList(String username, String subject){
        return realm.where(Homework.class).contains("ownerUsername", username).contains("subject", subject).findAll();
    }

    public void deleteHomework(Homework h){
        realm.beginTransaction();
        try{
            h.deleteFromRealm();
        }catch(Exception e){
            e.printStackTrace();
        }
        realm.commitTransaction();
    }
    public void close(){
        realm.close();
    }
}
