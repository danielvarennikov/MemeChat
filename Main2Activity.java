package com.example.memechat;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {
    DatabaseReference mRootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final TextView display= findViewById(R.id.display);

        //Get the nickname from the previous intent
        final String username= getIntent().getStringExtra("EXTRA_SESSION_ID_1");

        //Get the reference to your firebase database
        mRootRef= FirebaseDatabase.getInstance().getReference();

        Button post=(Button) findViewById(R.id.post);

        post.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                //A nice gamble that makes the "System" say a random message every now an then
                int dice=(int)(Math.random()*10);

                //In the database create a branch called log that will keep track of all the messages so that
                //we can display the messaging history
                DatabaseReference mLog=mRootRef.child("log");
                EditText input= (EditText) findViewById(R.id.input);

                //Get the current time so that we can later display when the message was sent
                String currentTime=new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

                //Create a branch in the log directory with the current time + the nicname that posted the message
                DatabaseReference newAdmin=mLog.child(currentTime+" "+username);
                newAdmin.setValue(input.getText().toString());

                //The random SYSTEM message
                if(dice==3|dice==4){
                    DatabaseReference shitPost=mLog.child(currentTime+" SYSTEM: ");
                    shitPost.setValue("THANK YOU");
                }

                final StringBuilder stringBuilder=new StringBuilder();
                mLog.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        //Get all the messages in the database from the log branch and display them
                        stringBuilder.append(dataSnapshot.getKey()+" "+dataSnapshot.getValue()+" \n");
                        display.setText(stringBuilder.toString());
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }
}
