package com.example.benedict.attendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Dashboard extends Fragment {

    CardView attendance, calendar, settings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        initializeComponents(view);
        clickListeners();

        return view;
    }

    void initializeComponents(View view){
        attendance = view.findViewById(R.id.attendanceCard);
        calendar = view.findViewById(R.id.calendarCard);
        settings = view.findViewById(R.id.settingCard);
    }

    void clickListeners(){
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), CheckAttendanceSectionList.class);
                startActivity(i);
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), AttendanceCalendar.class);
                startActivity(i);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
