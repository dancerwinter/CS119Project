package com.example.csproject;

import android.content.Context;
import android.content.SharedPreferences;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;


@EBean
public class MyPrefs {
    @Bean
    UserManager uman;

    private Context context;
    private SharedPreferences preferences;
    public MyPrefs(Context context){
        this.context = context;
        preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    public void setCurrentUser(String username){
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("currentUsername", username);
        edit.apply();
    }

    public void saveRememberedUserCredentials(String name, String pass){
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("rememberedUsername", name);
        edit.putString("rememberedPassword", pass);
        edit.apply();
    }
    public void clearRememberedUser(){
        SharedPreferences.Editor edit = preferences.edit();
        edit.remove("rememberedUsername");
        edit.apply();
    }
    public void clearCurrentUser(){
        SharedPreferences.Editor edit = preferences.edit();
        edit.remove("currentUsername");
        edit.remove("currentPassword");
        edit.apply();
    }

    public ArrayList<String> getRememberedCredentials(){
        String name = preferences.getString("rememberedUsername", "this is bad");
        String pass = preferences.getString("rememberedPassword", "this is bad");
        ArrayList<String> creds = new ArrayList<String>();
        creds.add(name);
        creds.add(pass);
        return creds;
    }

    public String getCurrentUser(){
        return preferences.getString("currentUsername", "this is bad");
    }
}
