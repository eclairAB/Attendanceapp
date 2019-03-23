package com.example.benedict.attendanceapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddSectionDialog extends AppCompatDialogFragment {

    Global global = new Global();
    public SQLiteDatabase db;
    DatabaseHelper dh = null;

    EditText tSection;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_section_dialog, null);

        dh = new DatabaseHelper(this.getContext());
        db = dh.getWritableDatabase();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        initializeComponents(view);

        return builder.create();
    }

    void initializeComponents(final View view){
        tSection = view.findViewById(R.id.txtSection);
        view.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tSection.getText().toString().trim().equals("")){
                    Toast.makeText(getActivity(), "Please fill out the field", Toast.LENGTH_SHORT).show();
                }
                else if(dh.isSectionExisting(tSection.getText().toString())){
                    Toast.makeText(getActivity(), "Section named: \"" + tSection.getText().toString() +
                            "\" already exists", Toast.LENGTH_SHORT).show();
                }
                else {
                    addSection();
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

    void addSection(){
        db.execSQL("insert into section (name) values('"+ global.filter(tSection) +"')");
        tSection.setText("");
        Toast.makeText(getActivity(), "Section added", Toast.LENGTH_SHORT).show();
    }
}
