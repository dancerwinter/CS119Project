package com.example.csproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @Click(R.id.button)
    public void add() {
        AddClass_.intent(this).start();
    }

}
