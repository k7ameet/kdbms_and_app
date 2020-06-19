package com.example.kdbms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ListViewer extends AppCompatActivity {

    /*
    This class was meant to be used for the list view function.
    That function was later merged into another class.
    This class is still left available for edit, as the list view function could be refined in the future.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_viewer);
    }
}
