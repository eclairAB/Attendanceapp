package com.example.benedict.attendanceapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benedict.attendanceapp.Adapter.CustomAdapter;
import com.example.benedict.attendanceapp.Adapter.StudentList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class SelectedClassSchedule extends AppCompatActivity {

    SQLiteDatabase db;
    DatabaseHelper dh = new DatabaseHelper(this);

    Global global = new Global();

    ArrayList<String> arrayList = new ArrayList<>();

    final List<StudentList> student = new ArrayList<>();
    ArrayList<String> numbersList = new ArrayList<>();
    CustomAdapter adapter;

    java.util.Calendar calendar = java.util.Calendar.getInstance();
    final int dateToday = calendar.get(Calendar.DAY_OF_WEEK);

    private static final int SMS_PERMISSION = 0;


    String section;

    TextView subject, schedule;
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

        subject.setText(Objects.requireNonNull(getIntent().getExtras()).getString("subject"));
        section = Objects.requireNonNull(getIntent().getExtras()).getString("section");

        populate();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case SMS_PERMISSION:{

                if(grantResults.length>=0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    executeLoop();
                }
                else {
                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    void executeLoop(){

        for (int i = 0; i < numbersList.size(); i++){
            SendMessage(numbersList.get(i));
        }
    }

    void SendMessage(String number){

        String message = "Greetings of peace.\nPlease be notified that your son/daughter was unable to attend the class.";

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, message, null, null);
        Toast.makeText(this, "Sending message", Toast.LENGTH_SHORT).show();
    }

    void initializeComponent(){

        subject = findViewById(R.id.className);
        schedule = findViewById(R.id.scheduleTime);
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
//                Toast.makeText(SelectedClassSchedule.this, "marked items: "+arrayList.toString(), Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(SelectedClassSchedule.this);
                builder.setCancelable(false);
                builder.setTitle("");
                builder.setMessage("You are about to send message to absent students' guardian to inform them that " +
                        "their son/daughter is unable to attend the class. Are you sure you want to continue?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        try {
                            int permissionCheck = ContextCompat.checkSelfPermission(SelectedClassSchedule.this, Manifest.permission.SEND_SMS);

                            if(permissionCheck == PackageManager.PERMISSION_GRANTED){

                                executeLoop();
                            }
                            else {
                                ActivityCompat.requestPermissions(SelectedClassSchedule.this,
                                        new String[]{Manifest.permission.SEND_SMS},
                                        SMS_PERMISSION);
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create().show();
            }
        });
    }

    String getDayOfWeek(){
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

    void populate(){
        try {
            student.clear();
            numbersList.clear();

            Cursor data = dh.getStudentAttendance(getDayOfWeek(), global.stringFilter(section));
            if (data.getCount() == 0) {
                listView.setEnabled(false);
                String[] blank = {"No data to be shown"};
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, blank);
                listView.setAdapter(arrayAdapter);
            } else {
                listView.setEnabled(true);
                while (data.moveToNext()) {
                    student.add(new StudentList(false, data.getString(0)));
                    numbersList.add(data.getString(1));
                    listView.setAdapter(adapter);
                }
            }
        }
        catch (Exception e){
            Log.d("appERR",e.toString());
        }
    }
}
