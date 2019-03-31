package com.example.benedict.attendanceapp;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class AttendanceCheckMultipleItems extends AppCompatDialogFragment {

    Global global = new Global();
    public SQLiteDatabase db;
    DatabaseHelper dh = null;

    ListView listView;
    String listItem, dayOfWeek;
    TextView textView;

    ArrayList<String> list = new ArrayList<>();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_section_list, null);

        dh = new DatabaseHelper(this.getContext());
        db = dh.getWritableDatabase();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);



        initializeComponent(view);
        populate();

        return builder.create();
    }

    void initializeComponent(View view){
        listItem = Objects.requireNonNull(getArguments()).getString("listItem");
        dayOfWeek = Objects.requireNonNull(getArguments()).getString("dayOfWeek");

        textView = view.findViewById(R.id.sectionText);  textView.setText(listItem);
        listView = view.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getContext(), SelectedClassSchedule.class);
                i.putExtra("section", listView.getItemAtPosition(position).toString());
                startActivity(i);
            }
        });
    }

    public void populate(){
        try {
            list.clear();

            Cursor data = dh.getSectionIII(global.stringFilter(listItem), global.stringFilter(dayOfWeek));
            if (data.getCount() == 0) {
                listView.setEnabled(false);
                String[] blank = {"No data to be shown"};
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(this.getContext()), android.R.layout.simple_list_item_1, blank);
                listView.setAdapter(arrayAdapter);
            } else {
                listView.setEnabled(true);
                while (data.moveToNext()) {
                    list.add(data.getString(0));
                    ListAdapter listAdapter = new ArrayAdapter<>(Objects.requireNonNull(this.getContext()), android.R.layout.simple_list_item_1, list);
                    listView.setAdapter(listAdapter);
                }
            }
        }
        catch (Exception e){
            Log.d("appERR",e.toString());
        }
    }
}
