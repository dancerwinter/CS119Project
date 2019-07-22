package com.example.csproject;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

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
}
