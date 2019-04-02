package com.example.benedict.attendanceapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class AddSchedule extends AppCompatActivity  implements TimePicker.DialogListener{

    public SQLiteDatabase db;
    DatabaseHelper dh = new DatabaseHelper(this);
    Global global = new Global();

    String buttonID;

    AutoCompleteTextView t_Subject, t_Room;

    TextView sectionName;
    CheckBox cMonday, cTuesday, cWednesday, cThursday, cFriday, cSaturday;
    LinearLayout mondayLayout2, tuesdayLayout2, wednesdayLayout2, thursdayLayout2, fridayLayout2, saturdayLayout2;

    Button mondayStart;
    Button tuesdayStart;
    Button wednesdayStart;
    Button thursdayStart;
    Button fridayStart;
    Button saturdayStart;
    Button mondayEnd;
    Button tuesdayEnd;
    Button wednesdayEnd;
    Button thursdayEnd;
    Button fridayEnd;
    Button saturdayEnd;

    TimePicker timePicker = new TimePicker();

    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        db = dh.getWritableDatabase();

        initializeComponents();
        hideComponent();
        listeners();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        populateRoom();
        populateSubject();
    }

    void initializeComponents(){
        sectionName = findViewById(R.id.section);   sectionName.setText(getIntent().getExtras().getString("section"));

        t_Subject = findViewById(R.id.txtSubject);
        t_Room = findViewById(R.id.txtRoom);

        cMonday = findViewById(R.id.cMonday);
        cTuesday = findViewById(R.id.cTuesday);
        cWednesday = findViewById(R.id.cWednesday);
        cThursday = findViewById(R.id.cThursday);
        cFriday = findViewById(R.id.cFriday);
        cSaturday = findViewById(R.id.cSaturday);

        mondayLayout2 = findViewById(R.id.lMonday2);
        tuesdayLayout2 = findViewById(R.id.lTuesday2);
        wednesdayLayout2 = findViewById(R.id.lWednesday2);
        thursdayLayout2 = findViewById(R.id.lThursday2);
        fridayLayout2 = findViewById(R.id.lFriday2);
        saturdayLayout2 = findViewById(R.id.lSaturday2);

        mondayStart = findViewById(R.id.mondayStart);
        mondayEnd = findViewById(R.id.mondayEnd);
        tuesdayStart = findViewById(R.id.tuesdayStart);
        tuesdayEnd = findViewById(R.id.tuesdayEnd);
        wednesdayStart = findViewById(R.id.wednesdayStart);
        wednesdayEnd = findViewById(R.id.wednesdayEnd);
        thursdayStart = findViewById(R.id.thursdayStart);
        thursdayEnd = findViewById(R.id.thursdayEnd);
        fridayStart = findViewById(R.id.fridayStart);
        fridayEnd = findViewById(R.id.fridayEnd);
        saturdayStart = findViewById(R.id.saturdayStart);
        saturdayEnd = findViewById(R.id.saturdayEnd);
    }

    void listeners(){
        mondayStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getButton(mondayStart);
            }
        });
        mondayEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getButton(mondayEnd);
            }
        });

        tuesdayStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getButton(tuesdayStart);
            }
        });
        tuesdayEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getButton(tuesdayEnd);
            }
        });

        wednesdayStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getButton(wednesdayStart);
            }
        });
        wednesdayEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getButton(wednesdayEnd);
            }
        });

        thursdayStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getButton(thursdayStart);
            }
        });
        thursdayEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getButton(thursdayEnd);
            }
        });

        fridayStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getButton(fridayStart);
            }
        });
        fridayEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getButton(fridayEnd);
            }
        });

        saturdayStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getButton(saturdayStart);
            }
        });
        saturdayEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getButton(saturdayEnd);
            }
        });
    }

    @Override
    public void setTexts(String time) {
        if(buttonID.equals("2131296413")) mondayStart.setText(time);
        if(buttonID.equals("2131296412")) mondayEnd.setText(time);

        if(buttonID.equals("2131296530")) tuesdayStart.setText(time);
        if(buttonID.equals("2131296529")) tuesdayEnd.setText(time);

        if(buttonID.equals("2131296551")) wednesdayStart.setText(time);
        if(buttonID.equals("2131296550")) wednesdayEnd.setText(time);

        if(buttonID.equals("2131296515")) thursdayStart.setText(time);
        if(buttonID.equals("2131296514")) thursdayEnd.setText(time);

        if(buttonID.equals("2131296364")) fridayStart.setText(time);
        if(buttonID.equals("2131296363")) fridayEnd.setText(time);

        if(buttonID.equals("2131296446")) saturdayStart.setText(time);
        if(buttonID.equals("2131296445")) saturdayEnd.setText(time);
//        Log.d("buttonID", "time: "+time);
    }

    public void getButton(Button button){
        buttonID = String.valueOf(button.getId());
        timePicker.show(getSupportFragmentManager(), "time picker");
        Log.d("buttonID", "buttonID: "+buttonID);
    }

    void hideComponent(){
        mondayLayout2.setVisibility(View.INVISIBLE);
        tuesdayLayout2.setVisibility(View.INVISIBLE);
        wednesdayLayout2.setVisibility(View.INVISIBLE);
        thursdayLayout2.setVisibility(View.INVISIBLE);
        fridayLayout2.setVisibility(View.INVISIBLE);
        saturdayLayout2.setVisibility(View.INVISIBLE);
    }


    public void populateSubject(){

        try {
            list.clear();
            Cursor dataSource = dh.autoFillSubjectI();
            while (dataSource.moveToNext()) {
                list.add(dataSource.getString(0));
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
                t_Subject.setAdapter(arrayAdapter);
            }
        }
        catch (Exception e){
            Log.d("apperror",e.toString());
        }

    }

    public void populateRoom(){

        try {
            list.clear();
            Cursor dataSource = dh.autoFillRoom();
            while (dataSource.moveToNext()) {
                list.add(dataSource.getString(0));
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
                t_Room.setAdapter(arrayAdapter);
            }
        }
        catch (Exception e){
            Log.d("apperror",e.toString());
        }

    }


    public void addSubject(View view) {
        AddSubjectDialog addSubjectDialog = new AddSubjectDialog();
        addSubjectDialog.show(getSupportFragmentManager(), "add subject dialog");
    }

    public void addRoom(View view) {
        AddRoomDialog addRoomDialog = new AddRoomDialog();
        addRoomDialog.show(getSupportFragmentManager(), "add room dialog");
    }

    public void monday(View view) {
        if(cMonday.isChecked()) {
            mondayLayout2.setVisibility(View.VISIBLE);
        } else {
            mondayLayout2.setVisibility(View.INVISIBLE);
            mondayStart.setText("Start");
            mondayEnd.setText("End");
        }
    }

    public void tuesday(View view) {
        if(cTuesday.isChecked()) {
            tuesdayLayout2.setVisibility(View.VISIBLE);
        } else {
            tuesdayLayout2.setVisibility(View.INVISIBLE);
            tuesdayStart.setText("Start");
            tuesdayEnd.setText("End");
        }
    }

    public void wednesday(View view) {
        if(cWednesday.isChecked()) {
            wednesdayLayout2.setVisibility(View.VISIBLE);
        } else {
            wednesdayLayout2.setVisibility(View.INVISIBLE);
            wednesdayStart.setText("Start");
            wednesdayEnd.setText("End");
        }
    }

    public void thursday(View view) {
        if(cThursday.isChecked()) {
            thursdayLayout2.setVisibility(View.VISIBLE);
        } else {
            thursdayLayout2.setVisibility(View.INVISIBLE);
            thursdayStart.setText("Start");
            thursdayEnd.setText("End");
        }
    }

    public void friday(View view) {
        if(cFriday.isChecked()) {
            fridayLayout2.setVisibility(View.VISIBLE);
        } else {
            fridayLayout2.setVisibility(View.INVISIBLE);
            fridayStart.setText("Start");
            fridayEnd.setText("End");
        }
    }

    public void saturday(View view) {
        if(cSaturday.isChecked()) {
            saturdayLayout2.setVisibility(View.VISIBLE);
        } else {
            saturdayLayout2.setVisibility(View.INVISIBLE);
            saturdayStart.setText("Start");
            saturdayEnd.setText("End");
        }
    }


    public void saveClick(View view) {

        try {
            if (global.filter(t_Room).equals("") || global.filter(t_Subject).equals("")) {

                Toast.makeText(this, "Please indicate the room and the subject of this schedule", Toast.LENGTH_SHORT).show();
            } else {
                if (!cMonday.isChecked() && !cTuesday.isChecked() && !cWednesday.isChecked() &&
                        !cThursday.isChecked() && !cFriday.isChecked() && !cSaturday.isChecked()) {

                    Toast.makeText(this, "No day selected. Please add at least one item for the schedule", Toast.LENGTH_SHORT).show();
                } else {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(AddSchedule.this);
                    builder.setCancelable(false);
                    builder.setTitle("");
                    builder.setMessage("Confirm adding schedule for the section "+global.getTextView_unfiltered(sectionName)+"?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            save();
                            Toast.makeText(AddSchedule.this, "Schedule added", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.create().show();
                }
            }
        } catch (Exception e) {
            Log.d("apperr", "saveClick: "+e);
        }
    }

    void save(){
        String id;

        Cursor getId = db.rawQuery("select id from schedule where section_name ='"+ global.getTextView_filtered(sectionName) +"'",null); // set schedule_id for schedule_time table
        getId.moveToFirst();

        id = getId.getString(getId.getColumnIndex("id"));
        getId.close();

        insertSchedule();

        if (cMonday.isChecked()) dh.insertSchedule_time(id, "Monday", global.filter(t_Room),
                global.getButtonText(mondayStart), global.getButtonText(mondayEnd));

        if (cTuesday.isChecked()) dh.insertSchedule_time(id, "Tuesday", global.filter(t_Room),
                global.getButtonText(tuesdayStart), global.getButtonText(tuesdayEnd));

        if(cWednesday.isChecked()) dh.insertSchedule_time(id, "Wednesday", global.filter(t_Room),
                global.getButtonText(wednesdayStart), global.getButtonText(wednesdayEnd));

        if(cThursday.isChecked()) dh.insertSchedule_time(id, "Thursday", global.filter(t_Room),
                global.getButtonText(thursdayStart), global.getButtonText(thursdayEnd));

        if(cFriday.isChecked()) dh.insertSchedule_time(id, "Friday", global.filter(t_Room),
                global.getButtonText(fridayStart), global.getButtonText(fridayEnd));

        if(cSaturday.isChecked()) dh.insertSchedule_time(id, "Saturday", global.filter(t_Room),
                global.getButtonText(saturdayStart), global.getButtonText(saturdayEnd));
    }

    public void insertSchedule() {
        db.execSQL("insert into schedule('section_name', 'subject') values("+
                "'" + global.getTextView_filtered(sectionName) + "', " +
                "'" + global.filter(t_Subject) + "')");

    }

}
