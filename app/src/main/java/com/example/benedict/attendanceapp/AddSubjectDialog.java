package com.example.benedict.attendanceapp;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddSubjectDialog extends AppCompatDialogFragment {

    Global global = new Global();
    public SQLiteDatabase db;
    DatabaseHelper dh = null;

    EditText tSubject, tUnit;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_subject_dialog, null);

        dh = new DatabaseHelper(this.getContext());
        db = dh.getWritableDatabase();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        initializeComponents(view);

        return builder.create();
    }

    void initializeComponents(final View view){
        tSubject = view.findViewById(R.id.txtSubject);
        tUnit = view.findViewById(R.id.txtUnit);
        view.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(global.w_out_filter(tSubject).equals("") || global.w_out_filter(tUnit).equals("")){
                    Toast.makeText(getActivity(), "Please fill out the fields", Toast.LENGTH_SHORT).show();
                }
                else if(dh.isSubjectExisting(tSubject.getText().toString())){
                    Toast.makeText(getActivity(), "Subject named: \"" + tSubject.getText().toString() +
                            "\" already exists", Toast.LENGTH_SHORT).show();
                }
                else {
                    addSubject();
                }
            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    void addSubject(){
        db.execSQL("insert into subjects (title, unit) values('"+ global.filter(tSubject) +"', '"+ global.filter(tUnit) +"')");
        tSubject.setText("");
        tUnit.setText("");
        Toast.makeText(getActivity(), "Subject added", Toast.LENGTH_SHORT).show();
    }
}
