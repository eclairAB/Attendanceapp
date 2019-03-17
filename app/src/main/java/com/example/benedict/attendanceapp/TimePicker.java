package com.example.benedict.attendanceapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.Calendar;

public class TimePicker extends DialogFragment {

    String time;
    DialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        /*Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);*/


        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {

                if(hourOfDay == 24) time = String.format("%02d:%02d", 12, minute) + " AM";
                else if(hourOfDay > 11){
                    if(hourOfDay == 12) time = String.format("%02d:%02d", hourOfDay, minute) + " PM";
                    else{
                        if(hourOfDay == 13) time = String.format("%02d:%02d", 1, minute) + " PM";
                        if(hourOfDay == 14) time = String.format("%02d:%02d", 2, minute) + " PM";
                        if(hourOfDay == 15) time = String.format("%02d:%02d", 3, minute) + " PM";
                        if(hourOfDay == 16) time = String.format("%02d:%02d", 4, minute) + " PM";
                        if(hourOfDay == 17) time = String.format("%02d:%02d", 5, minute) + " PM";
                        if(hourOfDay == 18) time = String.format("%02d:%02d", 6, minute) + " PM";
                        if(hourOfDay == 19) time = String.format("%02d:%02d", 7, minute) + " PM";
                        if(hourOfDay == 20) time = String.format("%02d:%02d", 8, minute) + " PM";
                        if(hourOfDay == 21) time = String.format("%02d:%02d", 9, minute) + " PM";
                        if(hourOfDay == 22) time = String.format("%02d:%02d", 10, minute) + " PM";
                        if(hourOfDay == 23) time = String.format("%02d:%02d", 11, minute) + " PM";
                    }
                }
                else{
                    time = String.format("%02d:%02d", hourOfDay, minute) + " AM";
                }


                listener.setTexts(String.valueOf(time));
            }
        },12, 0, false);

        timePickerDialog.show();

        return timePickerDialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "implementation");
        }
    }

    public interface DialogListener{
        void setTexts(String time);
    }
}
