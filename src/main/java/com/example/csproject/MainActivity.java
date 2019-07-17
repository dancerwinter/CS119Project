package com.example.csproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;

    @AfterViews
    public void init() {
        // the layout for the recycler is the class_row.xml
    }

    @Click(R.id.addClassBtn)
    public void add() {
        AddClass_.intent(this).start();
    }

    @Click(R.id.manageSubjBtn)
    public void manage() {
        ManageSubjects_.intent(this).start();
    }

    @Click(R.id.logoutBtn)
    public void logout() {
        finish();
    }

}
