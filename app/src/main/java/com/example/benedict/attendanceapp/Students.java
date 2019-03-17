package com.example.benedict.attendanceapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class Students extends Fragment implements SearchView.OnQueryTextListener {

    public SQLiteDatabase db;
    DatabaseHelper dh = null;

    ListView listView;
    SearchView searchView;

    ArrayList<String> id_index = new ArrayList<>();
    ArrayList<String> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_students, container, false);


        dh = new DatabaseHelper(this.getContext());
        db = dh.getWritableDatabase();

        initializeComponents(view);

        populate();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        populate();
    }
    private void initializeComponents(View view){
        searchView = view.findViewById(R.id.searchView); searchView.setOnQueryTextListener(this);
        listView = view.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                long index = listView.getItemIdAtPosition(position);

                Intent i = new Intent(Students.this.getContext(),SelectedStudent.class);
                i.putExtra("val", id_index.get((int) index));
                startActivity(i);
            }
        });
    }

    public void populate(){
        try {
            id_index.clear();
            list.clear();

            Cursor data = dh.getStudent(searchView.getQuery().toString());
            if (data.getCount() == 0) {
                listView.setEnabled(false);
                String[] blank = {"No data to be shown"};
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(this.getContext()), android.R.layout.simple_list_item_1, blank);
                listView.setAdapter(arrayAdapter);
            } else {
                listView.setEnabled(true);
                while (data.moveToNext()) {
                    id_index.add(data.getString(0)); // will fetch id for indexing
                    list.add(data.getString(2));
                    ListAdapter listAdapter = new ArrayAdapter<>(Objects.requireNonNull(this.getContext()), android.R.layout.simple_list_item_1, list);
                    listView.setAdapter(listAdapter);
                }
            }
        }
        catch (Exception e){
            Log.d("appERR",e.toString());
        }
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
