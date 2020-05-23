package com.example.kdbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterListDetails extends AppCompatActivity {

    EditText comp_name, comp_addr;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_list_details);
        comp_name = findViewById(R.id.company_name_et);
        comp_name.setText("");
        comp_addr = findViewById(R.id.address_et);
        comp_addr.setText("");
        button = findViewById(R.id.enter_details_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { buttonClick(); }});
    }

    private void buttonClick() {
        if (comp_name.getText().toString().equals("") || comp_addr.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Please enter all required details", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getApplicationContext(), ListScan.class);
            intent.putExtra("comp_name", comp_name.getText().toString());
            intent.putExtra("comp_addr", comp_addr.getText().toString());
            startActivity(intent);
        }
    }
}
