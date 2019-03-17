package com.example.benedict.attendanceapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    public SQLiteDatabase db;
    DatabaseHelper dh = new DatabaseHelper(this);

    DatabaseReference databaseReference, login;
    EditText tName, tPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = dh.getWritableDatabase();

        databaseReference = FirebaseDatabase.getInstance().getReference("accounts");


        LinearLayout layout = findViewById(R.id.layout);
        tName = findViewById(R.id.txtUname);
        tPass = findViewById(R.id.txtPass);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        layout.startAnimation(myanim);

        Thread timer = new Thread()
        {
            public  void  run()
            {
                try
                {
                    sleep(1000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    // finish();
                }
            }
        };
        timer.start();
    }

    public void login(View view) {



        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    /*void login(final String username, final String password){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(username).exists()){
                    if(!username.isEmpty()){
                        String compare = dataSnapshot.child(username).getValue(tPass.getText().toString());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/
}
