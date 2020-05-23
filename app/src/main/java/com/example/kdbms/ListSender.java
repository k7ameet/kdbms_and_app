package com.example.kdbms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ListSender extends AppCompatActivity {

    TextView tvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sender);
        tvv = findViewById(R.id.tvv);
        tvv.setText(getIntent().getStringExtra("json_object")+getIntent().getStringExtra("comp_name")+getIntent().getStringExtra("comp_addr"));
    }
}
