package com.example.csproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_add_class)
public class AddClass extends AppCompatActivity {

    @Click(R.id.acAddBtn)
    public void add() {
        finish();
    }

}
