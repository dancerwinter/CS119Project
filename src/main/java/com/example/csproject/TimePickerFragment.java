package com.example.csproject;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

// https://android--examples.blogspot.com/2015/04/timepickerdialog-in-android.html
// https://developer.android.com/guide/topics/ui/controls/pickers#java

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private EditText tv;

    public TimePickerFragment(EditText textView) {
        tv = textView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        if (minute < 10)
            tv.setText(String.valueOf(hourOfDay)+"0"+String.valueOf(minute));
        else
            tv.setText(String.valueOf(hourOfDay)+String.valueOf(minute));
    }
}