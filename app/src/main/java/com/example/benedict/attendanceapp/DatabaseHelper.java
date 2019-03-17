package com.example.benedict.attendanceapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB = "app.db";

    DatabaseHelper(Context context) {
        super(context, DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        createTables(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        dropTables(sqLiteDatabase);
    }

    private void createTables(SQLiteDatabase sqLiteDatabase){

        sqLiteDatabase.execSQL("create table if not exists account(" +
                "'id' integer primary key autoincrement, " +
                "'userlevel' text, " +
                "'username' text, " +
                "'password' text, " +
                "'fname' text, " +
                "'lname' text, " +
                "'subjects' text, " +
                "'sections' text)");

        sqLiteDatabase.execSQL("create table if not exists student(" +
                "'id' integer primary key autoincrement, " +
                "'teacher_id' integer, " +
                "'fullname' text, " +
                "'fname'text, " +
                "'lname' text, " +
                "'section' text, " +
                "'year' text, " +
                "'status' text, " + // regular or irregular
                "'contactno' text, " +
                "'guardian' text, " +
                "'guardianno' text)");

        sqLiteDatabase.execSQL("create table if not exists subjects(" +
                "'id' integer primary key autoincrement, " +
                "'student_id' integer, " +
                "'title' text, " +
                "'unit' text)");

        sqLiteDatabase.execSQL("create table if not exists section(" +
                "'id' integer primary key autoincrement, " +
                "'name' text)");

        sqLiteDatabase.execSQL("create table if not exists schedule(" +
                "'id' integer primary key autoincrement, " +
                "'subject' text, " +
                "'time' text, " +
                "'date' text, " +
                "'room' text)");

        sqLiteDatabase.execSQL("create table if not exists room(" +
                "'id' integer primary key autoincrement, " +
                "'name' text)");

        sqLiteDatabase.execSQL("create table if not exists attendance_days(" +
                "'id' integer primary key autoincrement, " +
                "'schedule_id' integer, " +
                "'date' text)");

        sqLiteDatabase.execSQL("create table if not exists attendance_students(" +
                "'id' integer primary key autoincrement, " +
                "'student_id' integer)");
    }

    private void dropTables(SQLiteDatabase sqLiteDatabase){

        sqLiteDatabase.execSQL("drop table if exists account");
        sqLiteDatabase.execSQL("drop table if exists student");
        sqLiteDatabase.execSQL("drop table if exists subjects");
        sqLiteDatabase.execSQL("drop table if exists section");
        sqLiteDatabase.execSQL("drop table if exists schedule");
        sqLiteDatabase.execSQL("drop table if exists room");
        sqLiteDatabase.execSQL("drop table if exists attendance_days");
        sqLiteDatabase.execSQL("drop table if exists attendance_students");
        this.onCreate(sqLiteDatabase);
    }


    Cursor getStudent(String text){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from student where fname like '%"+text+"%' or lname like '%"+text+"%' or fullname like '%"+text+"%' order by fullname asc",null);
    } // populating listView and search usage

    Cursor autoFillSection(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select name from section order by name asc",null);
    } // data source for section AutoCompleteTextView

    Boolean isStudentExisting(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select fullname from student where fullname = '" + name + "'",null);
        if(data.getCount() == 0){
            return false;
        }
        else {
            return true;
        }
    } // returns true if name is already existing

    Boolean isSectionExisting(String section){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select name from section where name = '" + section + "'",null);
        if(data.getCount() == 0){
            return false;
        }
        else {
            return true;
        }
    } // returns true if section is already existing


    void deleteStudent(String index){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from student where id = '"+ index +"'");
    }

    void updateInfo(String index,    String fullname,   String fname, String lname,   String contact,
                    String guardian, String guardianno, String year,  String section, String status){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update student set fullname = '" + fullname +
                "', fname = '" + fname +
                "', lname = '" + lname +
                "', section = '" + section +
                "', year = '" + year +
                "', status = '" +  status +
                "', contactno = '" + contact +
                "', guardian = '" + guardian +
                "', guardianno = '" + guardianno + "' " +
                "where id = '" + index + "'");
    }
}
