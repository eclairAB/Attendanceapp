package com.example.benedict.attendanceapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SelectedStudent extends AppCompatActivity {

    public SQLiteDatabase db;
    DatabaseHelper dh = new DatabaseHelper(this);

    Global global = new Global();

    LinearLayout display_show, display_edit;
    String index;
    TextView studentname;
    TextView fname, lname, contact, guardian, guardian_contact, year, section, status;
    EditText t_fname, t_lname, t_contact, t_guardian, t_guardian_contact, t_year, t_section;
    Spinner s_status;
    ImageView edit_save, delete_cancel;

    boolean toggled_btn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_student);

        db = dh.getWritableDatabase();

        initializeComponents();

        populateFields();

        mode_view();
    }

    void initializeComponents(){
        display_show = findViewById(R.id.data_view);
        display_edit = findViewById(R.id.data_edit);

        studentname = findViewById(R.id.student_name);              index = getIntent().getExtras().getString("val");
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        contact = findViewById(R.id.contact);
        guardian = findViewById(R.id.guardian);
        guardian_contact = findViewById(R.id.guardian_contact);
        year = findViewById(R.id.year);
        section = findViewById(R.id.section);
        status = findViewById(R.id.status);

        t_fname = findViewById(R.id.txtFname);
        t_lname = findViewById(R.id.txtLname);
        t_contact = findViewById(R.id.txtContact);
        t_guardian = findViewById(R.id.txtGuardian);
        t_guardian_contact = findViewById(R.id.txtGuardian_contact);
        t_year = findViewById(R.id.txtYear);
        t_section = findViewById(R.id.txtSection);
        s_status = findViewById(R.id.selection_status);

        edit_save = findViewById(R.id.btn1);
        delete_cancel = findViewById(R.id.btn2);
    }

    void populateFields(){
        Cursor getName = db.rawQuery("select * from student where id = '"+ index +"'",null);
        getName.moveToFirst();

        studentname.setText(getName.getString(getName.getColumnIndex("fullname")));
        fname.setText(getName.getString(getName.getColumnIndex("fname")));
        lname.setText(getName.getString(getName.getColumnIndex("lname")));
        contact.setText(getName.getString(getName.getColumnIndex("contactno")));
        guardian.setText(getName.getString(getName.getColumnIndex("guardian")));
        guardian_contact.setText(getName.getString(getName.getColumnIndex("guardianno")));
        year.setText(getName.getString(getName.getColumnIndex("year")));
        section.setText(getName.getString(getName.getColumnIndex("section")));
        status.setText(getName.getString(getName.getColumnIndex("status")));

        getName.close();
    }

    void passText(){
        int arrayindex = 0;
        if(global.getTextView_unfiltered(status).equals("Regular")) arrayindex = 0;
        if(global.getTextView_unfiltered(status).equals("Transferee")) arrayindex = 1;
        if(global.getTextView_unfiltered(status).equals("New student")) arrayindex = 2;

        global.textviewToEditText(fname, t_fname);
        global.textviewToEditText(lname, t_lname);
        global.textviewToEditText(contact, t_contact);
        global.textviewToEditText(guardian, t_guardian);
        global.textviewToEditText(guardian_contact, t_guardian_contact);
        global.textviewToEditText(year, t_year);
        global.textviewToEditText(section, t_section);
        s_status.setSelection(arrayindex);
    }

    void toggle(){
        if(toggled_btn){
            mode_view();
            edit_save.setImageResource(R.drawable.ic_edit);
            delete_cancel.setImageResource(R.drawable.ic_delete_forever_black_24dp);
            toggled_btn = !toggled_btn;
        }
        else {
            mode_edit();
            edit_save.setImageResource(R.drawable.ic_save);
            delete_cancel.setImageResource(R.drawable.ic_cancel);
            toggled_btn = !toggled_btn;
            passText();
        }
    }

    void mode_view(){
        display_show.setVisibility(View.VISIBLE);
        display_edit.setVisibility(View.GONE);
    }

    void mode_edit(){
        display_show.setVisibility(View.GONE);
        display_edit.setVisibility(View.VISIBLE);
    }

    public void edit_save(View view) {
        toggle();

        if(!toggled_btn){

            final AlertDialog.Builder builder = new AlertDialog.Builder(SelectedStudent.this);
            builder.setCancelable(false);
            builder.setTitle("");
            builder.setMessage("Are you sure you want to update records of "+studentname.getText()+"?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    String t_fullname = t_fname.getText() + " " + t_lname.getText();

                    dh.updateInfo(index, t_fullname, t_fname.getText().toString(), t_lname.getText().toString(), t_contact.getText().toString(),
                                  t_guardian.getText().toString(), t_guardian_contact.getText().toString(), t_year.getText().toString(),
                                  t_section.getText().toString(), s_status.getSelectedItem().toString());

                    Toast.makeText(SelectedStudent.this, "Update successful", Toast.LENGTH_SHORT).show();
                    populateFields();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                toggle();
                }
            });
            builder.create().show();

        }
        else {

        }
    }

    public void delete_cancel(View view) {

        if(!toggled_btn){

            final AlertDialog.Builder builder = new AlertDialog.Builder(SelectedStudent.this);
            builder.setCancelable(false);
            builder.setTitle("");
            builder.setMessage("Are you sure you want to remove records of "+studentname.getText()+"?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dh.deleteStudent(index);
                    Toast.makeText(SelectedStudent.this, "Deleted successfully", Toast.LENGTH_SHORT).show();

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
        else {
            toggle();

//            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
