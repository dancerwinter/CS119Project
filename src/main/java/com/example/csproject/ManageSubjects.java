package com.example.csproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import io.realm.RealmResults;

@EActivity(R.layout.activity_manage_subjects)
public class ManageSubjects extends AppCompatActivity {

    @ViewById(R.id.recyclerView2)
    RecyclerView recyclerView;

    @Extra
    String userUUID;

    @Bean
    UserManager uman;
    @Bean
    MyPrefs prefs;
    @Bean
    ClassManager cman;
    private User currentUser;

    @AfterViews
    public void init() {
        //get current user name from prefs
        String name = prefs.getCurrentUser();
        currentUser = uman.getUser(name);
        // the layout for the recycler is the class_edit.xml
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        RealmResults<Class> classList = cman.getUserClasses(currentUser.getUUID());
        ClassManAdapter cma = new ClassManAdapter(classList, this);
        recyclerView.setAdapter(cma);
        uman.close();


        // RealmResults<Class> classList = classMng.findAll();

        // ClassAdapter ca = new ClassAdapter(this, classList);
        // recyclerView.setAdapter(ca);
    }

    @Click(R.id.msAddClassBtn)
    public void add() {
        cman.close();
        AddClass_.intent(this).start();
    }

    @Click(R.id.backBtn)
    public void back() {
        finish();
    }

    public void goToEdit(Class c){
        editClass_.intent(this)
                .classUUID(c.getUUID())
                .username(currentUser.getUsername())
                .start();
//        cman.close();
    }
    public void deleteClass(Class c){
        cman.deleteClass(c);
    }
}
