package com.example.benedict.attendanceapp;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benedict.attendanceapp.Adapter.CustomAdapter;
import com.example.benedict.attendanceapp.Adapter.StudentList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class SelectedClassSchedule extends AppCompatActivity {

    SQLiteDatabase db;
    DatabaseHelper dh = new DatabaseHelper(this);

    ArrayList<String> arrayList = new ArrayList<>();

    final List<StudentList> student = new ArrayList<>();
    CustomAdapter adapter;

    TextView section, schedule;
    SearchView searchView;
    ListView listView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_class_schedule);

        db = dh.getWritableDatabase();
        adapter = new CustomAdapter(SelectedClassSchedule.this, student);


        initializeComponent();
        listeners();

        section.setText(Objects.requireNonNull(getIntent().getExtras()).getString("listItem"));

        populate();
    }

    void initializeComponent(){

        section = findViewById(R.id.className);
        schedule = findViewById(R.id.scheduleTime);
        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.listView);
        button = findViewById(R.id.button);
    }

    void listeners(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                StudentList model = student.get(position);

                if (model.isSelected()) {
                    model.setSelected(false);

                    arrayList.remove(model.getstudentName());
                } else {
                    model.setSelected(true);

                    arrayList.add(model.getstudentName());
                }

                student.set(position, model);
                adapter.updateRecords(student);

                if (arrayList.size() == 0) {
                    button.setEnabled(false);
                } else {
                    button.setEnabled(true);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SelectedClassSchedule.this, "marked items: "+arrayList.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void populate(){
        student.add(new StudentList(false, "Student1"));
        student.add(new StudentList(false, "Student2"));
        student.add(new StudentList(false, "Student3"));
        student.add(new StudentList(false, "Student4"));
        student.add(new StudentList(false, "Student5"));
        student.add(new StudentList(false, "Student6"));
        student.add(new StudentList(false, "Student7"));
        student.add(new StudentList(false, "Student8"));
        student.add(new StudentList(false, "Student9"));
        student.add(new StudentList(false, "Student10"));
        student.add(new StudentList(false, "Student11"));
        student.add(new StudentList(false, "Student12"));
        student.add(new StudentList(false, "Student13"));
        student.add(new StudentList(false, "Student14"));

        listView.setAdapter(adapter);
    }
}
