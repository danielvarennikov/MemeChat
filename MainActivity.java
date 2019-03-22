package com.example.memechat;
//Just the front page of the app that lets you pick a nickname and start chatting
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //The button that transfers you to the chat app itself
        Button shit=(Button) findViewById(R.id.button);

        shit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText username= (EditText) findViewById(R.id.editText);

                //Get the desired nicname from the text box
                String user=username.getText().toString();

                //Move to the the main page of the app, transfer the nickname to it
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("EXTRA_SESSION_ID_1", user);
                startActivity(intent);

            }
        });

    }
}
