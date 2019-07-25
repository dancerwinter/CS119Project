package com.example.csproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_add_homework)
public class AddHomework extends AppCompatActivity {

    @Click(R.id.ahwCancel)
    public void cancel() {
        finish();
    }

    @Click(R.id.ahwAdd)
    public void add() {
        finish();
    }

}
