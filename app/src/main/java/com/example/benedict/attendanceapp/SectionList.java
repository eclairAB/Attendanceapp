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
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Objects;

public class SectionList extends AppCompatDialogFragment implements SearchView.OnQueryTextListener{

    Global global = new Global();
    public SQLiteDatabase db;
    DatabaseHelper dh = null;

    ListView listView;
    SearchView searchView;

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

    @Override
    public void onResume() {
        super.onResume();

        populate();
    }

    void initializeComponent(View view){
        searchView = view.findViewById(R.id.searchView);  searchView.setOnQueryTextListener(this);
        listView = view.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                long index = listView.getItemIdAtPosition(position);

                Intent i = new Intent(getContext(), AddSchedule.class);
                i.putExtra("section", listView.getItemAtPosition(position).toString());
                startActivity(i);
            }
        });
    }

    public void populate(){
        try {
            list.clear();

            Cursor data = dh.getSectionII(global.getSVquery(searchView));
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
