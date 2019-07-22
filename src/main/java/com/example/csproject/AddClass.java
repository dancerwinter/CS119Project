package com.example.csproject;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_add_class)
public class AddClass extends AppCompatActivity {

    @ViewById(R.id.edSubject)
    EditText acSubject;
    @ViewById(R.id.edBuilding)
    EditText acBuilding;
    @ViewById(R.id.edRoom)
    EditText acRoom;
    @ViewById(R.id.edSection)
    EditText acSection;
    @ViewById(R.id.edTeacher)
    EditText acTeacher;
    @ViewById(R.id.edTimeStart)
    EditText acTimeStart;
    @ViewById(R.id.edTimeEnd)
    EditText acTimeEnd;

    // Repeating days views

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
    String userUUID;

    @Bean
    MyPrefs prefs;

    @Bean
    ClassManager cman;
    @Bean
    UserManager uman;

    private User currentUser;
    @AfterViews
    public void init(){
        String name = prefs.getCurrentUser();
        currentUser = uman.getUser(name);
    }



    @Click(R.id.edSaveBtn)
    public void add() {
        if (isFilled()) {
            Boolean mon = cbMon.isChecked();
            Boolean tues = cbTue.isChecked();
            Boolean wed = cbWed.isChecked();
            Boolean thu = cbThu.isChecked();
            Boolean fri = cbFri.isChecked();
            Boolean sat = cbSat.isChecked();
            Boolean sun = cbSun.isChecked();

            String subject = acSubject.getText().toString();
            String section = acSection.getText().toString();
            String building = acBuilding.getText().toString();
            String room = acRoom.getText().toString();
            String teacher = acTeacher.getText().toString();
            String timeStart = acTimeStart.getText().toString();
            String timeEnd = acTimeEnd.getText().toString();

            Class c = cman.createClass(subject, section, building,room, teacher, timeStart, timeEnd, currentUser.getUUID(),mon, tues, wed, thu, fri, sat, sun);

            cman.addClass(c);
            finish();
        }else{
            pop("Please fill all fields");
        }



    }

    private Boolean isFilled(){
        Boolean repeatFilled = cbMon.isChecked() || cbTue.isChecked()||cbWed.isChecked() || cbThu.isChecked() || cbFri.isChecked() || cbSat.isChecked() || cbSun.isChecked();
        String subject = acSubject.getText().toString();
        String section = acSection.getText().toString();
        String building = acBuilding.getText().toString();
        String room = acRoom.getText().toString();
        String teacher = acTeacher.getText().toString();
        String timeStart = acTimeStart.getText().toString();
        String timeEnd = acTimeEnd.getText().toString();
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
