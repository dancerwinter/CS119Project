package com.example.csproject;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;
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

    // adding photo
    @ViewById
    ImageView imageViewClass;

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
        checkPermissions();
    }

    public void checkPermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA

                )

                .withListener(new BaseMultiplePermissionsListener() {
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            loadPhoto();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "You must provide permissions for app to run", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                })
                .check();
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

    @Click(R.id.acAddPic)
    public void selectImg() {
        String name = acSubject.getText().toString();

        if (name.equals("")) {
            popup("Please input first a subject name");
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

        String name = acSubject.getText().toString();
        System.out.println(name);
        File savedImage = new File(getImageDir, name);

        if (savedImage.exists()) {
            refreshImageView(savedImage);
        }
    }

    private File saveFile(byte[] jpeg, String name) throws IOException {
        // gets the sd card path
        File getImageDir = getExternalCacheDir();

        String filename = name + ".jpeg";

        // change image name to username + number in array
        File savedImage = new File(getImageDir, filename);

        FileOutputStream fos = new FileOutputStream(savedImage);
        fos.write(jpeg);
        fos.close();
        return savedImage;
    }

    public void onActivityResult(int requestCode, int responseCode, Intent data) {
        if (requestCode==0) {
            if (responseCode==100) {
                // save rawImage to file savedImage.jpeg
                // load file via picasso
                byte[] jpeg = data.getByteArrayExtra("rawJpeg");
                String name = data.getStringExtra("name");

                try {
                    File savedImage = saveFile(jpeg, name);
                    refreshImageView(savedImage);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void refreshImageView(File savedImage) {
        Picasso.with(this)
                .load(savedImage)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(imageViewClass);
    }

    public void popup(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}
