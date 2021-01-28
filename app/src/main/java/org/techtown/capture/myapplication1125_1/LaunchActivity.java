package org.techtown.capture.myapplication1125_1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        Button mode1_button = findViewById(R.id.mode1_button);
        Button mode2_button = findViewById(R.id.mode2_button);

        Intent mode1_intent = new Intent(LaunchActivity.this,MainActivity.class);
        //Intent mode2_intent = new Intent(getApplicationContext(),Main2Activity.class);

        mode1_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mode1_intent);finish();
            }
        });

        mode2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(mode2_intent);finish();
            }
        });

    }
}