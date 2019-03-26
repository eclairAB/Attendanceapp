package com.example.benedict.attendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, // launches student fragment on start
                    new Dashboard()).commit();

            navigationView.setCheckedItem(R.id.nav_dashboard);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Dashboard()).commit();
                break;

            case R.id.nav_students:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Students()).commit();
                break;

            case R.id.nav_subjects:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Subjects()).commit();
                break;

            case R.id.nav_sections:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Sections()).commit();
                break;

             case R.id.nav_schedules:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Schedules()).commit();
                break;

             case R.id.nav_rooms:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Rooms()).commit();
                break;

            case R.id.add_student:
                Intent intent1 = new Intent(this,AddStudent.class);
                startActivity(intent1);
                break;

            case R.id.add_subject:
                AddSubjectDialog addSubjectDialog = new AddSubjectDialog();
                addSubjectDialog.show(getSupportFragmentManager(), "add subject dialog");
                break;

            case R.id.add_section:
                AddSectionDialog addSectionDialog = new AddSectionDialog();
                addSectionDialog.show(getSupportFragmentManager(), "add section dialog");
                break;

            case R.id.add_schedule:
                Intent intent4 = new Intent(this,AddScheduleList.class);
                startActivity(intent4);
                break;

            case R.id.add_room:
                AddRoomDialog addRoomDialog = new AddRoomDialog();
                addRoomDialog.show(getSupportFragmentManager(), "add room dialog");
                break;

            // ADD MORE CASES FOR "ADD" FIELDS
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        try {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
        catch (Exception e){
            Log.d("err123",e.toString());
        }
    }
}
