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

import java.util.Calendar;

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
        Calendar calander = Calendar.getInstance();
        int cDay = calander.get(Calendar.DAY_OF_MONTH);
        int cMonth = calander.get(Calendar.MONTH) + 1;
        int cYear = calander.get(Calendar.YEAR);

        int weekDay = dayofweek(cDay,cMonth,cYear);
//        System.out.println(weekDay);
        // the layout for the recycler is the class_row.xml
        user = uman.getUser(uname);
        classList = cman.getUserClassForToday(weekDay,user.getUUID());
        int classSize = classList.size();
        String cPlur = "class";
        if(classSize > 1){
            cPlur = "classes";
        }
        welcomeText.setText("\nWelcome " + uname + "\nYou have " + classSize + " " + cPlur+ " " + "today");


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        ClassAdapter ca = new ClassAdapter(classList, this);
        recyclerView.setAdapter(ca);

        // testing date and time

//        cMonth = calander.get(Calendar.MONTH) + 1;
//        cYear = calander.get(Calendar.YEAR);
//        selectedMonth = "" + cMonth;
//        selectedYear = "" + cYear;
//        cHour = calander.get(Calendar.HOUR);
//        cMinute = calander.get(Calendar.MINUTE);
//        cSecond = calander.get(Calendar.SECOND);


    }

    public void onResume(){
        super.onResume();
        int classSize = classList.size();
        String cPlur = "class";
        if(classSize > 1){
            cPlur = "classes";
        }
        welcomeText.setText("\nWelcome " + uname + "\nYou have " + classSize + " " + cPlur+ " " + "today");
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
    // A program to find day of a given date
    //https://www.geeksforgeeks.org/find-day-of-the-week-for-a-given-date/
    private int dayofweek(int d, int m, int y)
    {
        int t[] = { 0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4 };
        y -= (m < 3) ? 1 : 0;
        return ( y + y/4 - y/100 + y/400 + t[m-1] + d) % 7;
    }
    // 0:Sunday ,1:Monday, 2:Tuesday, 3: Wednesday, 4: Thursday, 5: Friday, 6: Saturday

//        // Driver Program to test above function
//        public static void main(String arg[])
//        {
//            int day = dayofweek(30, 8, 2010);
//            System.out.print(day);
//        }


// This code is contributed
// by Anant Agarwal.

}
