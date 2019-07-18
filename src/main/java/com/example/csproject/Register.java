package com.example.csproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_register)
public class Register extends AppCompatActivity {

    @ViewById(R.id.regUsername)
    EditText username;
    @ViewById(R.id.regPassword)
    EditText password;
    @ViewById(R.id.regEmail)
    EditText email;

    @Bean
    UserManager uman;

    @AfterViews
    public void init(){}

    @Click(R.id.regRegisterBtn)
    public void register() {
        String un = username.getText().toString();
        String pass = password.getText().toString();
        String em = email.getText().toString();

        //check first if username is being used
        if(!uman.nameIsUsed(un)){
            // checks if the stuff is filled
            if(fieldsAreFilled(un,pass,em)){
                //if not used then do stuff here
                User u = uman.createUser(un,pass,em);
                uman.addUser(u);
                pop("User with username: " + un + " has been added!");
                finish();
            }
            else{
                pop("Please fill up all fields!");
            }


        }
        else{
            pop("Username is already taken please enter a different username");
        }
//        finish();
    }

    @Click(R.id.regCancelBtn)
    public void cancel() {
        finish();
    }

    public boolean fieldsAreFilled(String un, String pass, String em){
        return !un.isEmpty() && !pass.isEmpty() && !em.isEmpty();
    }

    public void pop(String s){
        Toast.makeText(this,s, Toast.LENGTH_LONG).show();
    }


}
