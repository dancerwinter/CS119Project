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
        System.out.println("Homework Added");
        RealmResults<Homework> homeworkList = getAllHomework();
//        for(Homework hh:homeworkList){
//            System.out.println(hh.getTitle());
//        }
    }

    public Homework createHomework(String title, String description, String username, String subject){
        Homework h = new Homework();
        h.setTitle(title);
        h.setDescription(description);
        System.out.println(username + "addhw");
        h.setOwnerUsername(username);
        h.setSubject(subject);
        return h;
    }
    public RealmResults<Homework> getAllHomework(){
        return realm.where(Homework.class).findAll();
    }
    public RealmResults<Homework> getHomeworkList(String username, String subject){
        RealmResults<Homework> homeworkList = getAllHomework();

//        for(Homework h : homeworkList){
//            System.out.println(h.getOwnerUsername());
//        }

        return realm.where(Homework.class).contains("ownerUsername", username).and().contains("subject", subject).findAll();
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
