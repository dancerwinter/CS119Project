package com.example.csproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.io.File;

import io.realm.RealmResults;

@EActivity(R.layout.activity_class_details)
public class ClassDetails extends AppCompatActivity {

    @Extra
    String classUUID;

    @ViewById(R.id.recyclerView3)
    RecyclerView recyclerView;
    @ViewById(R.id.cdSubject)
    TextView cdSubj;
    @ViewById(R.id.cdTeacher)
    TextView cdTeacher;
    @ViewById(R.id.cdTime)
    TextView cdTime;
    @ViewById(R.id.cdLocation)
    TextView cdLocation;
    @ViewById(R.id.cdRepeats)
    TextView cdRepeats;
    @ViewById(R.id.imageView2)
    ImageView imageView;

    @Bean
    ClassManager cman;
    @Bean
    UserManager uman;
    @Bean
    HomeworkManager hman;
    private Class classDeets;
    private User user;
    @AfterViews
    public void init() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        classDeets = cman.getClass(classUUID);
        user = uman.getUser2(classDeets.getUserUUID());
        cdSubj.setText(classDeets.getSubject());
        cdTeacher.setText(classDeets.getTeacher());
        String timeBuilder = classDeets.getTimeStart() + " - " + classDeets.getTimeEnd();
        cdTime.setText(timeBuilder);
        cdLocation.setText(classDeets.getBuilding() + " " + classDeets.getRoom());
        cdRepeats.setText(repeatMaker(classDeets.getMonday(),classDeets.getTuesday(),classDeets.getWednesday(),classDeets.getThursday(),classDeets.getFriday(),classDeets.getSaturday(),classDeets.getSunday()));

        File getImageDir = getExternalCacheDir();
        File savedImage = new File(getImageDir,classDeets.getSubject() + user.getUsername() + ".jpeg");
        if(savedImage.exists()){
            System.out.println("file existss");
            refreshImageView(savedImage);
        }

        RealmResults<Homework> homeworkList = hman.getHomeworkList(user.getUsername(),classDeets.getSubject());
        for(Homework h: homeworkList){
            System.out.println(h.getSubject() + "deets");
        }
        HomeworkAdapter hwa = new HomeworkAdapter(homeworkList, this);
        recyclerView.setAdapter(hwa);
    }
    private void refreshImageView(File savedImage) {
        Picasso.with(this)
                .load(savedImage)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(imageView);
    }

    @Click(R.id.cdAddHW)
    public void addHomework() {
        cman.close();
        uman.close();
        finish();
        AddHomework_.intent(this)
                .username(user.getUsername())
                .classUUID(classUUID)
                .start();
    }

    public void deleteHW(Homework h){
        hman.deleteHomework(h);
    }
    private String repeatMaker(Boolean mon, Boolean tue, Boolean wed, Boolean thu, Boolean fri, Boolean sat, Boolean sun){
        String result = "";
        if(mon){
            result += "M ";
        }
        if(tue){
            result += "T ";
        }
        if(wed){
            result += "W ";
        }
        if(thu){
            result += "Th ";
        }
        if(fri){
            result += "F ";
        }
        if(sat){
            result += "S ";
        }
        if(sun){
            result += "Sn ";
        }

        return result;
    }
}
