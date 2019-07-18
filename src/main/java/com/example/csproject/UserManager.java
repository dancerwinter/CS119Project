package com.example.csproject;

import org.androidannotations.annotations.EBean;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

@EBean
public class UserManager {

    private Realm realm;

    public UserManager() {
        realm = Realm.getDefaultInstance();
    }

    // stuff here

    // creates User object given the arguments

    public User createUser(String name, String password, String email){
        User u = new User();
        u.setUUID(UUID.randomUUID().toString());
        u.setUsername(name);
        u.setPassword(password);
        u.setEmail(email);
        return u;
    }

    // returns realmResult of all currently listed users in the Realm
    public RealmResults<User> getUserList(){
        RealmResults<User> userList = realm.where(User.class).findAll();
        return userList;
    }

    // checks if name is already being used by another user
    public Boolean nameIsUsed(String name){
        RealmResults<User> userList = getUserList();
        System.out.println("isInList method");
        for(User u: userList){
            if(u.getUsername().equals(name)){
                return true;
            }
        }
        return false;
    }
    // adds user to Realm

    public void addUser(User u){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(u);
        realm.commitTransaction();
    }

    //deletes user from realm
    public void deletDis(User u){
        realm.beginTransaction();
        try{
            u.deleteFromRealm();
        }catch(Exception e){
            e.printStackTrace();
        }
        realm.commitTransaction();
    }

    public Boolean isUser(String name, String password){
        if(realm.where(User.class).count() == 0){
            return false;
        }

        // check if the username is in the userList
        if(!nameIsUsed(name)){
            System.out.println("Username not found");
            return false;
        }
        //if username is in the list, check if password is correct

        return comparePass(name, password);
    }
    private Boolean comparePass(String name, String password){
        RealmResults<User> userList = getUserList();
        for(User u: userList){
            if(u.getUsername().equals(name)&&u.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    public User getUser(String name){
        RealmResults<User> userList = getUserList();
        User result = null;
        for(User u: userList){
            if(u.getUsername().equals(name)){
                result = u;
                return result;
            }
        }
        return result;
    }
    public void close() {
        realm.close();
    }
}
