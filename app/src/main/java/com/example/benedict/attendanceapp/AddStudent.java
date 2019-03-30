package com.example.benedict.attendanceapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class AddStudent extends AppCompatActivity {

    public SQLiteDatabase db;
    DatabaseHelper dh = new DatabaseHelper(this);
    Global global = new Global();

    EditText t_fname, t_lname, t_contact, t_guardian, t_guardian_contact, t_year;
    Spinner t_status;
    AutoCompleteTextView t_section;

    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        db = dh.getWritableDatabase();

        initializeComponents();

        populateAutoComplete();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        populateAutoComplete();
    }

    public void initializeComponents(){
        t_fname = findViewById(R.id.txtFname);
        t_lname = findViewById(R.id.txtLname);
        t_contact = findViewById(R.id.txtContact);
        t_guardian = findViewById(R.id.txtGuardian);
        t_guardian_contact = findViewById(R.id.txtGuardian_contact);
        t_year = findViewById(R.id.txtYear);
        t_section = findViewById(R.id.txtSection);
        t_status = findViewById(R.id.selection_status);

        listeners();
    }

    public void listeners(){

        t_fname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (global.isContainingDigit(t_fname))
                    Toast.makeText(AddStudent.this, "First name cannot contain digits", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        t_lname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (global.isContainingDigit(t_lname))
                    Toast.makeText(AddStudent.this, "Last name cannot contain digits", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        t_guardian.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (global.isContainingDigit(t_guardian))
                    Toast.makeText(AddStudent.this, "Guardian cannot contain digits", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void insert() {
        String fullname = "" + global.filter(t_fname) + " " + global.filter(t_lname);

        db.execSQL("insert into student('fullname', 'fname', 'lname', 'contactno', 'guardian', 'guardianno', 'section', 'year', 'status') values("+
                "'" + fullname + "', " +
                "'" + global.filter(t_fname) + "', " +
                "'" + global.filter(t_lname) + "', " +
                "'" + global.filter(t_contact) + "', " +
                "'" + global.filter(t_guardian) + "', " +
                "'" + global.filter(t_guardian_contact) + "', " +
                "'" + global.filter(t_section) + "', " +
                "'" + global.filter(t_year) + "', " +
                "'" + global.getSpinner_filtered(t_status) + "')");

                clear();

        Toast.makeText(this, "Student added", Toast.LENGTH_SHORT).show();
    }

    public void clear(){
        t_fname.setText("");
        t_lname.setText("");
        t_contact.setText("");
        t_guardian.setText("");
        t_guardian_contact.setText("");
        t_year.setText("");
        t_section.setText("");
    }

    public void populateAutoComplete(){

        try {
            list.clear();
            Cursor section_dataSource = dh.autoFillSection();
            while (section_dataSource.moveToNext()) {
                list.add(section_dataSource.getString(0));
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(this.getBaseContext()), android.R.layout.simple_list_item_1, list);
                t_section.setAdapter(arrayAdapter);
            }
        }
        catch (Exception e){
            Log.d("apperror",e.toString());
        }

    }

    public void save(View view) {

        if (t_fname.getText().toString().equals("") || t_lname.getText().toString().equals("") || t_contact.getText().toString().equals("") || t_guardian.getText().toString().equals("") ||
                t_guardian_contact.getText().toString().equals("") || t_year.getText().toString().equals("") || t_section.getText().toString().equals("") || t_status.getSelectedItem().toString().equals("")) {

            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                String fullname = "" + global.getEditText_unfiltered(t_fname) + " " + global.getEditText_unfiltered(t_lname);

                if (dh.isStudentExisting(fullname)) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(AddStudent.this);
                    builder.setCancelable(false);
                    builder.setTitle("");
                    builder.setMessage("You already have record with name \n\""+ fullname +"\". Continue to add anyway?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            insert();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.create().show();
                }
                else {
                    insert();
                }
            } catch (Exception e) {
                Log.d("appERR", e.toString());
            }
        }
    }

    public void addSection(View view) {
        AddSectionDialog addSectionDialog = new AddSectionDialog();
        addSectionDialog.show(getSupportFragmentManager(), "add section dialog");
    }
}
