package com.example.benedict.attendanceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AttendaceCalendarSelected extends AppCompatActivity {

    TextView displayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendace_calendar_selected);

        displayDate = findViewById(R.id.selectedDateTxt);

        displayDate.setText(getIntent().getExtras().getString("date"));
    }
}
