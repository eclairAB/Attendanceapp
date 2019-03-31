package com.example.benedict.attendanceapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Dashboard extends Fragment {

    Global global = new Global();
    public SQLiteDatabase db;
    DatabaseHelper dh = null;

    CardView attendance, calendar, settings;

    EditText queryBox;
    Button btnExcute;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        dh = new DatabaseHelper(this.getContext());
        db = dh.getWritableDatabase();

        initializeComponents(view);
        clickListeners();

        return view;
    }

    void initializeComponents(View view){
        attendance = view.findViewById(R.id.attendanceCard);
        calendar = view.findViewById(R.id.calendarCard);
        settings = view.findViewById(R.id.settingCard);

        queryBox = view.findViewById(R.id.queryBox);
        btnExcute = view.findViewById(R.id.execute);
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

        btnExcute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(global.getEditText_unfiltered(queryBox).contains("insert into")){

                    db.execSQL(global.getEditText_unfiltered(queryBox));
                    Toast.makeText(getActivity(), "command executed", Toast.LENGTH_SHORT).show();
                    queryBox.setText("");
                }
                else {
                    Cursor cursor = db.rawQuery(global.filter(queryBox), null);
                    Log.d("cursor_output", String.valueOf(cursor));
                    queryBox.setText("");
                }
            }
        });
    }
}
