package com.example.csproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_register)
public class Register extends AppCompatActivity {

    @Click(R.id.regRegisterBtn)
    public void register() {
        finish();
    }

    @Click(R.id.regCancelBtn)
    public void cancel() {
        finish();
    }

}
