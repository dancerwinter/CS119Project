package com.example.csproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_class_details)
public class ClassDetails extends AppCompatActivity {

    @ViewById(R.id.recyclerView3)
    RecyclerView recyclerView;

    @AfterViews
    public void init() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        //HomeworkAdapter hwa = new ClassAdapter(classList, this);
        //recyclerView.setAdapter(hwa);
    }

    @Click(R.id.cdAddHW)
    public void addHomework() {
        AddHomework_.intent(this).start();
    }

}
