package com.example.kdbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText Username;
    EditText Password;
    Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Username = findViewById(R.id.inputUsername);
        Password = findViewById(R.id.inputPassword);
        Login = findViewById(R.id.btnLogin);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkDetails(Username.getText().toString(), Password.getText().toString())){
                    Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                    startActivity(intent);
                } else {
                    Password.setText("");
                    Toast.makeText(getApplicationContext(), "Incorrect login details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkDetails(String username, String password){
        if(password.equals("hunter2!") && username.equals("admin")){
            return true;
        } return false;
    }













}
