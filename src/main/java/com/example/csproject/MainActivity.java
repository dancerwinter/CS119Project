package com.example.csproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import io.realm.RealmResults;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @Extra
    String uname;
    @Bean
    MyPrefs prefs;
    @Bean
    UserManager uman;
    @Bean
    ClassManager cman;
    @ViewById(R.id.welcomeText)
    TextView welcomeText;
    private User user;
    private RealmResults<Class> classList;

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;

    @AfterViews
    public void init() {
        // the layout for the recycler is the class_row.xml
        user = uman.getUser(uname);
        welcomeText.setText("\nWelcome " + uname + "\nYou have 3 classes today\n");
        classList = cman.getUserClasses(user.getUUID());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        ClassAdapter ca = new ClassAdapter(classList, this);
        recyclerView.setAdapter(ca);



    }

    @Click(R.id.addClassBtn)
    public void add() {
        AddClass_.intent(this)
                .userUUID(user.getUUID())
                .start();

    }

    @Click(R.id.manageSubjBtn)
    public void manage() {
        ManageSubjects_.intent(this)
                .userUUID(user.getUUID())
                .start();
    }

    @Click(R.id.logoutBtn)
    public void logout() {
        prefs.clearCurrentUser();
        finish();
    }

}
