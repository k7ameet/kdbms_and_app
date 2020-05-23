package com.example.kdbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    Button button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        button1 = findViewById(R.id.home_button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { homeButtons(1); }});
        button2 = findViewById(R.id.home_button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { homeButtons(2); }});

    }

    private void homeButtons(int ind){
        if (ind == 1){
            Intent intent1 = new Intent(getApplicationContext(), SelectOption.class);
            startActivity(intent1);
        } else if (ind == 2){
            Intent intent2 = new Intent(getApplicationContext(), EnterListDetails.class);
            startActivity(intent2);
        }
    }
}
