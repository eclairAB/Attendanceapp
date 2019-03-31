package com.example.benedict.attendanceapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class CheckAttendanceSectionList extends AppCompatActivity {

    public SQLiteDatabase db;
    DatabaseHelper dh = new DatabaseHelper(this);
    Global global = new Global();

    ListView listView;

    ArrayList<String> list = new ArrayList<>();

    java.util.Calendar calendar = java.util.Calendar.getInstance();
    final int dateToday = calendar.get(Calendar.DAY_OF_WEEK);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_attendance_section_list);

        db = dh.getWritableDatabase();

        initializeComponents();
        listeners();

        populate();
    }

    void initializeComponents(){
        listView = findViewById(R.id.listView);
    }

    void listeners(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String listItem = listView.getItemAtPosition(position).toString();

                if(dh.isMultipleSchedule(listItem)){

                    Bundle bundle = new Bundle();
                    bundle.putString("listItem", listItem);
                    bundle.putString("dayOfWeek", getDateOfWeek());

                    AttendanceCheckMultipleItems attendanceCheckMultipleItems = new AttendanceCheckMultipleItems();
                    attendanceCheckMultipleItems.setArguments(bundle);
                    attendanceCheckMultipleItems.show(getSupportFragmentManager(), "");
                }
                else {
                    Intent i = new Intent(CheckAttendanceSectionList.this, SelectedClassSchedule.class);
                    i.putExtra("listItem",listItem);
                    startActivity(i);

                    Toast.makeText(CheckAttendanceSectionList.this, "false", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    String getDateOfWeek(){
        String date = null;
        if (dateToday == 1) date = "Sunday";
        if (dateToday == 2) date = "Monday";
        if (dateToday == 3) date = "Tuesday";
        if (dateToday == 4) date = "Wednesday";
        if (dateToday == 5) date = "Thursday";
        if (dateToday == 6) date = "Friday";
        if (dateToday == 7) date = "Saturday";
        return date;
    }

    public void populate(){
            try {
                list.clear();

                Cursor data = dh.getScheduleForToday(getDateOfWeek());
                if (data.getCount() == 0) {
                    listView.setEnabled(false);
                    String[] blank = {"No class for today"};
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, blank);
                    listView.setAdapter(arrayAdapter);
                } else {
                    listView.setEnabled(true);
                    while (data.moveToNext()) {
                        list.add(data.getString(0));
                        ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
                        listView.setAdapter(listAdapter);
                    }
                }
            }
            catch (Exception e){
                Log.d("appERR",e.toString());
            }
        }
}
