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

public class AddRoomDialog extends AppCompatDialogFragment {

    Global global = new Global();
    public SQLiteDatabase db;
    DatabaseHelper dh = null;

    EditText tRoom;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_room_dialog, null);

        dh = new DatabaseHelper(this.getContext());
        db = dh.getWritableDatabase();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        initializeComponents(view);

        return builder.create();
    }

    void initializeComponents(final View view){
        tRoom = view.findViewById(R.id.txtRoom);
        view.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(global.getEditText_unfiltered(tRoom).equals("")){
                    Toast.makeText(getActivity(), "Please fill out the field", Toast.LENGTH_SHORT).show();
                }
                else if(dh.isRoomExisting(global.filter(tRoom))){
                    Toast.makeText(getActivity(), "Room named: \"" + tRoom.getText().toString() +
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
        db.execSQL("insert into room (name) values('"+ global.filter(tRoom) +"')");
        tRoom.setText("");
        Toast.makeText(getActivity(), "Room added", Toast.LENGTH_SHORT).show();
    }
}
