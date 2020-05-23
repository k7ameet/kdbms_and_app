package com.example.kdbms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ListScan extends AppCompatActivity {

    String comp_name, comp_addr;
    TextView tester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_scan);
        comp_name = getIntent().getStringExtra("comp_name");
        comp_addr = getIntent().getStringExtra("comp_addr");
        tester = findViewById(R.id.xyzz);
        String testerString = comp_name + comp_addr;
        tester.setText(testerString);

    }
}
