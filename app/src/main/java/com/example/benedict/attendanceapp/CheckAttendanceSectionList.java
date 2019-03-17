    package com.example.benedict.attendanceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

    public class CheckAttendanceSectionList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_attendance_section_list);

        final ListView listView = findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String listItem = listView.getItemAtPosition(position).toString();

                Intent i = new Intent(CheckAttendanceSectionList.this, SelectedClassSchedule.class);
                i.putExtra("listitem",listItem);
                startActivity(i);
            }
        });
    }
}
