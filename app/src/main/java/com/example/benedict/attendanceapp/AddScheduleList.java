package com.example.benedict.attendanceapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class AddScheduleList extends AppCompatActivity implements SearchView.OnQueryTextListener {

    Global global = new Global();

    public SQLiteDatabase db;
    DatabaseHelper dh = new DatabaseHelper(this);

    SearchView searchView;
    ListView listView;

    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule_list);

        db = dh.getWritableDatabase();

        initializeComponent();
        populate();
    }

    @Override
    protected void onResume() {
        super.onResume();

        populate();
    }

    void initializeComponent(){
        searchView = findViewById(R.id.searchView);  searchView.setOnQueryTextListener(this);
        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(), AddSchedule.class);
                i.putExtra("section",listView.getItemAtPosition(position).toString());
                startActivity(i);

            }
        });
    }

    public void populate(){
        try {
            list.clear();

            Cursor data = dh.getSectionI(global.getSVquery(searchView));
            if (data.getCount() == 0) {
                listView.setEnabled(false);
                String[] blank = {"No data to be shown"};
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, blank);
                listView.setAdapter(arrayAdapter);
            } else {
                listView.setEnabled(true);
                while (data.moveToNext()) {
                    list.add(data.getString(1));
                    ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
                    listView.setAdapter(listAdapter);
                }
            }
        }
        catch (Exception e){
            Log.d("appERR",e.toString());
        }
    }


    public void add(View view) {
        SectionList sectionList = new SectionList();
        sectionList.show(getSupportFragmentManager(), "section list dialog");
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        populate();
        return false;
    }
}