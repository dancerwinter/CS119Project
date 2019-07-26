package com.example.csproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

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

    @ViewById(R.id.etHWTitle)
    EditText hwTitle;
    @ViewById(R.id.etHWDesc)
    EditText hwDesc;

    private Class subjClass;
    private User user;



    @AfterViews
    public void init(){
        user = uman.getUser(username);
        subjClass = cman.getClass(classUUID);
    }


    @Click(R.id.ahwCancel)
    public void cancel() {
        cman.close();
        hman.close();
        hman.close();
        finish();
        ClassDetails_.intent(this).start();
    }

    @Click(R.id.ahwAdd)
    public void add() {
//        System.out.println(username + "ADDHW");
        Homework h = hman.createHomework(hwTitle.getText().toString(),hwDesc.getText().toString(),username,subjClass.getSubject());
        hman.addHomework(h);
        cman.close();
        hman.close();
        hman.close();
        finish();
        ClassDetails_.intent(this).start();
    }

}
