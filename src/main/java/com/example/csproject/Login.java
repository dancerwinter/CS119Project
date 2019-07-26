package com.example.csproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity(R.layout.activity_login)
public class Login extends AppCompatActivity {
    @ViewById(R.id.loginUsername)
    EditText username;
    @ViewById(R.id.loginPassword)
    EditText password;
    @ViewById(R.id.loginRemCB)
    CheckBox rememberBox;
    @Bean
    UserManager uman;
    @Bean
    MyPrefs prefs;

    private NotificationUtils mNotificationUtils;

    @AfterViews
    public void init(){
        ArrayList<String> rememberedCredentials = prefs.getRememberedCredentials();
        if(!rememberedCredentials.get(0).equals("this is bad")){
            username.setText(rememberedCredentials.get(0));
            password.setText((rememberedCredentials.get(1)));
        }
    }

    @Click(R.id.loginSignInBtn)
    public void signIn() {


       NotificationUtils m = new NotificationUtils(this);

       Notification n = m.getAndroidChannelNotification("Neil", "was here").build();
       m.getManager().notify(1, n);

        if(username.getText().toString().equals("") || password.getText().toString().equals("")){
            pop("please enter full credentials");
        }
        else{
            String un = username.getText().toString();
            String pass = password.getText().toString();
            //check if the user is in the system
            if(uman.isUser(un,pass)){
                prefs.setCurrentUser(un);
                //if rememberMe is checked, set remembered user in prefs
                if(rememberBox.isChecked()){
                    prefs.saveRememberedUserCredentials(un,pass);
                }
                else{
                    prefs.clearRememberedUser();
                }
                MainActivity_.intent(this)
                        .uname(un)
                        .start();
                uman.close();

            }
            else{
                pop("User with credentials not found, please enter the correct information");
                password.setText("");

            }
        }
//        MainActivity_.intent(this).start();
    }

    @Click(R.id.loginRegBtn)
    public void register() {
        Register_.intent(this).start();
        uman.close();
    }
    public void pop(String s){
        Toast.makeText(this,s, Toast.LENGTH_LONG).show();
    }
}
