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

    // ************************************************* create tables ********************************************************


    private void createTables(SQLiteDatabase sqLiteDatabase){

        /*
        *
        *      Note:
        *
        *      Indentation resembles table structure hierarchy
        *
        * */


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
                        "'section_name' text, " +
                        "'subject' text)");

                        sqLiteDatabase.execSQL("create table if not exists schedule_time(" +
                                "'id' integer primary key autoincrement, " +
                                "'schedule_id' integer, " +
                                "'day' text, " +
                                "'room' text, " +
                                "'start_time' text, " +
                                "'end_time' text)");

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
        sqLiteDatabase.execSQL("drop table if exists schedule_time");
        sqLiteDatabase.execSQL("drop table if exists room");
        sqLiteDatabase.execSQL("drop table if exists attendance_days");
        sqLiteDatabase.execSQL("drop table if exists attendance_students");
        this.onCreate(sqLiteDatabase);
    }


    // ************************************************* populate fields ********************************************************


    Cursor getStudent(String text){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from student where fname like '%"+text+"%' or lname like '%"+text+"%' or fullname like '%"+text+"%' order by fullname asc",null);
    } // populating student listView and search usage

    Cursor getSubject(String text){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from subjects where title like '%"+text+"%' order by title asc",null);
    } // populating student listView and search usage

    Cursor getSectionI(String text){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select distinct section_name from schedule where section_name like '%" + text + "%' having count(section_name)>0 order by section_name asc",null);
    } // populating section listView and search usage

    Cursor getSectionII(String text){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select name from section where name like '%" + text + "%' and id != (select section_id from schedule group by subject having count(distinct section_id)>0) order by name asc",null);

        /*if(sectionName.getCount() != 0){
            return  sectionName;
        }
        else {
            return db.rawQuery("select * from section where name like '%" + text + "%' ",null);
        }*/
    } // populating SectionList.java listView and search usage (returns all section without existing schedule)


    Cursor autoFillSection(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select name from section order by name asc",null);
    } // data source for section AutoCompleteTextView

    Cursor autoFillSubject(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select title from subjects order by title asc",null);
    } // data source for section AutoCompleteTextView

    Cursor autoFillRoom(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select name from room order by name asc",null);
    } // data source for section AutoCompleteTextView



    Cursor autoFillSectionI(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select distinct name from section where id != (select section_id from schedule group by subject having count(distinct section_id)>0) order by name asc",null);
    } // data source for section AutoCompleteTextView

    Cursor autoFillSubjectI(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor subjects = db.rawQuery("select distinct title from subjects where title != (select subject from schedule group by subject having count(distinct subject)>0) order by title asc",null);

        if(subjects.getCount() != 0){
            return  subjects;
        }
        else {
            return db.rawQuery("select distinct title from subjects order by title asc",null);
        }
    } // data source for section AutoCompleteTextView

    Cursor autoFillRoomI(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select name from room order by name asc",null);
    } // data source for section AutoCompleteTextView


    // ************************************************* existence checks ********************************************************


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

    Boolean isRoomExisting(String room){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select name from room where name = '" + room + "'",null);
        if(data.getCount() == 0){
            return false;
        }
        else {
            return true;
        }
    } // returns true if room is already existing

    Boolean isSubjectExisting(String subject){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select title from subjects where title = '" + subject + "'",null);
        if(data.getCount() == 0){
            return false;
        }
        else {
            return true;
        }
    } // returns true if subject is already existing


    // ************************************************* inserts ********************************************************

    void insertSchedule_time(Integer schedule_id, String day, String room, String start_time, String end_time) {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("insert into schedule_time('schedule_id', 'day', 'room', 'start_time', 'end_time') values("+
                "'" + schedule_id + "', " +
                "'" + day + "', " +
                "'" + room + "', " +
                "'" + start_time + "', " +
                "'" + end_time + "')");
    }


    // ************************************************* deletes ********************************************************


    void deleteStudent(String index){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from student where id = '"+ index +"'");
    }


    // ************************************************* updates ********************************************************


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
