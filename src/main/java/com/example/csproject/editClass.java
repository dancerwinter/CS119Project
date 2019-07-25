package com.example.csproject;

import android.content.Intent;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import java.io.FileOutputStream;
import java.io.IOException;

@EActivity(R.layout.activity_edit_class)
public class editClass extends AppCompatActivity {

    @ViewById(R.id.edSubject)
    EditText edSubj;
    @ViewById(R.id.edSection)
    EditText edSec;
    @ViewById(R.id.edBuilding)
    EditText edBuild;
    @ViewById(R.id.edRoom)
    EditText edRoom;
    @ViewById(R.id.edTeacher)
    EditText edTeacher;
    @ViewById(R.id.edTimeStart)
    EditText edTimeStart;
    @ViewById(R.id.edTimeEnd)
    EditText edTimeEnd;

    @ViewById(R.id.cbMon)
    CheckBox cbMon;
    @ViewById(R.id.cbTue)
    CheckBox cbTue;
    @ViewById(R.id.cbWed)
    CheckBox cbWed;
    @ViewById(R.id.cbThu)
    CheckBox cbThu;
    @ViewById(R.id.cbFri)
    CheckBox cbFri;
    @ViewById(R.id.cbSat)
    CheckBox cbSat;
    @ViewById(R.id.cbSun)
    CheckBox cbSun;

    @ViewById
    ImageView ecImageViewClass;

    @Extra
    String username;

    @Extra
    String classUUID;

    @Bean
    ClassManager cman;

    @Bean
    UserManager uman;

    private User user;
    private Class classEdited;

    @AfterViews
    public void init(){
        user = uman.getUser(username);
        classEdited = cman.getClass(classUUID);
        cbMon.setChecked(classEdited.getMonday());
        cbTue.setChecked(classEdited.getTuesday());
        cbWed.setChecked(classEdited.getWednesday());
        cbThu.setChecked(classEdited.getThursday());
        cbFri.setChecked(classEdited.getFriday());
        cbSat.setChecked(classEdited.getSaturday());
        cbSun.setChecked(classEdited.getSunday());

        edSubj.setText(classEdited.getSubject());
        edSec.setText(classEdited.getSection());
        edBuild.setText(classEdited.getBuilding());
        edRoom.setText(classEdited.getRoom());
        edTeacher.setText(classEdited.getTeacher());
        edTimeStart.setText(classEdited.getTimeStart());
        edTimeEnd.setText(classEdited.getTimeEnd());

        File getImageDir = getExternalCacheDir();
        File savedImage = new File(getImageDir, classEdited.getSubject() + user.getUsername()+ ".jpeg");
        System.out.println("init Edit");
        if (savedImage.exists()) {
            System.out.println("file exists");
            refreshImageView(savedImage);
        }

    }
    @Click(R.id.edCancelBtn)
    public void cancel(){
//        uman.close();
//        cman.close();
        finish();
    }

    @Click(R.id.edSaveBtn)
    public void saveEdits(){
        if (isFilled()) {
            Class editClass = new Class();
            editClass.setUUID(classUUID);
            editClass.setUserUUID(user.getUUID());
            editClass.setSubject(edSubj.getText().toString());
            editClass.setSection(edSec.getText().toString());
            editClass.setBuilding(edBuild.getText().toString());
            editClass.setRoom(edRoom.getText().toString());
            editClass.setTeacher(edTeacher.getText().toString());
            editClass.setTimeStart(edTimeStart.getText().toString());
            editClass.setTimeEnd(edTimeEnd.getText().toString());
            editClass.setMonday(cbMon.isChecked());
            editClass.setTuesday(cbTue.isChecked());
            editClass.setWednesday(cbWed.isChecked());
            editClass.setThursday(cbThu.isChecked());
            editClass.setFriday(cbFri.isChecked());
            editClass.setSaturday(cbSat.isChecked());
            editClass.setSunday(cbSun.isChecked());
            cman.addClass(editClass);
            cman.close();
            uman.close();
            finish();
        }
        else{
            pop("Please fill up all fields");
        }
    }

    private Boolean isFilled(){
        Boolean repeatFilled = cbMon.isChecked() || cbTue.isChecked()||cbWed.isChecked() || cbThu.isChecked() || cbFri.isChecked() || cbSat.isChecked() || cbSun.isChecked();
        String subject = edSubj.getText().toString();
        String section = edSec.getText().toString();
        String building = edBuild.getText().toString();
        String room = edRoom.getText().toString();
        String teacher = edTeacher.getText().toString();
        String timeStart = edTimeStart.getText().toString();
        String timeEnd = edTimeEnd.getText().toString();
        Boolean fieldsFilled = false;
        if(subject.isEmpty()||building.isEmpty()||room.isEmpty()||section.isEmpty()||teacher.isEmpty()||timeStart.isEmpty()||timeEnd.isEmpty()){
            fieldsFilled = false;
        }else{
            fieldsFilled = true;
        }
        return fieldsFilled && repeatFilled;
    }
    public void pop(String s){
        Toast.makeText(this,s, Toast.LENGTH_LONG).show();
    }

    @Click(R.id.ecChangePic)
    public void changePic() {
        String name = edSubj.getText().toString();

        if (name.equals("")) {
            pop("Please input first a subject name");
        }
        else {
            ClassPhotoCrop_.intent(this)
                    .extra("imgName", name)
                    .startForResult(0);
        }
    }
    public void loadPhoto() {
        // check if savedImage.jpeg exists in path
        // load via picasso if exists

        // change image name to username + number in array
        File getImageDir = getExternalCacheDir();

        String name = edSubj.getText().toString();
        System.out.println(name);
        File savedImage = new File(getImageDir, name);

        if (savedImage.exists()) {
            refreshImageView(savedImage);
        }
    }
    private void refreshImageView(File savedImage) {
        Picasso.with(this)
                .load(savedImage)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(ecImageViewClass);
    }

    public void onActivityResult(int requestCode, int responseCode, Intent data) {
        if (requestCode==0) {
            if (responseCode==100) {
                // save rawImage to file savedImage.jpeg
                // load file via picasso
                byte[] jpeg = data.getByteArrayExtra("rawJpeg");
                String name = data.getStringExtra("subjectName");

                try {
                    File savedImage = saveFile(jpeg, name);
                    System.out.println(name +user.getUsername()+ " onAct");
                    refreshImageView(savedImage);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private File saveFile(byte[] jpeg, String name) throws IOException {
        // gets the sd card path
        File getImageDir = getExternalCacheDir();

        String filename = name + user.getUsername()+ ".jpeg";

        // change image name to username + number in array
        File savedImage = new File(getImageDir, filename);

        FileOutputStream fos = new FileOutputStream(savedImage);
        fos.write(jpeg);
        fos.close();
        return savedImage;
    }
}
