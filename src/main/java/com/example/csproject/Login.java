package com.example.csproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_login)
public class Login extends AppCompatActivity {

    @Click(R.id.loginSignInBtn)
    public void signIn() {
        MainActivity_.intent(this).start();
    }

    @Click(R.id.loginRegBtn)
    public void register() {
        Register_.intent(this).start();
    }
}
