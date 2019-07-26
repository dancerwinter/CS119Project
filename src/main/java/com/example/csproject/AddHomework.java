package com.example.csproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.Extra;

@EActivity(R.layout.activity_add_homework)
public class AddHomework extends AppCompatActivity {
    @Bean
    ClassManager cman;
    @Bean
    UserManager uman;
    @Bean
    HomeworkManager hman;

    @Extra
    String classUUID;
    @Extra
    String username;


    @Click(R.id.ahwCancel)
    public void cancel() {
        finish();
    }

    @Click(R.id.ahwAdd)
    public void add() {

        finish();
    }

}
