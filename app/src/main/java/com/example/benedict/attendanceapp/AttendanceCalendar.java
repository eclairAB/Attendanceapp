package com.example.benedict.attendanceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;


public class AttendanceCalendar extends AppCompatActivity {

    CalendarView calendarView;

    String monthFiltered;
    int incrementedMonth;

    //============================================================================================== this line gets current date of month, month, year
    java.util.Calendar calendar = java.util.Calendar.getInstance();
    final int dateToday = calendar.get(Calendar.DAY_OF_MONTH);
    final int monthOfYear = calendar.get(Calendar.MONTH);
    final int yearToday = calendar.get(Calendar.YEAR);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                if(isNotAheadThanToday(year, month, dayOfMonth)){

                    Intent i = new Intent(AttendanceCalendar.this, AttendaceCalendarSelected.class);

                    incrementedMonth = month + 1;
                    if(incrementedMonth < 10) {
                        monthFiltered = String.valueOf(incrementedMonth);

                        i.putExtra("date",dayOfMonth +"/0"+ monthFiltered +"/"+year);
                    } else {
                        monthFiltered = String.valueOf(incrementedMonth);

                        i.putExtra("date",dayOfMonth +"/"+ monthFiltered +"/"+year);
                    }


                    startActivity(i);
                }
                else {
                    Toast.makeText(AttendanceCalendar.this, "No records yet for\ndates beyond today", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    Boolean isNotAheadThanToday(int year, int month, int dayOfMonth){
        return year <= yearToday && month <= monthOfYear && dayOfMonth <= dateToday;
    }
}
