package com.example.kdbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ListSender extends AppCompatActivity {

    Button sendBtn;
    String comp_name, comp_addr, jsonList;
    TextView textv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sender);
        textv = findViewById(R.id.list_display);
        sendBtn = findViewById(R.id.send_to_server_button);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendButton();
            }
        });
        comp_name = getIntent().getStringExtra("comp_name");
        comp_addr = getIntent().getStringExtra("comp_addr");
        jsonList = getIntent().getStringExtra("json_object");
        textv.setText(jsonList);
    }

    private void sendButton() {
        send_to_server();
    }

    /*
    Send the entire list to the server.
    This uses values passed on from previous activities.
     */

    private void send_to_server(){
        String requestUrl = "https://us-central1-korean-export-dbms.cloudfunctions.net/app/api/list/newlist/mobile";
        //String requestUrl = "https://ptsv2.com/t/wa6u0-1590294137/post";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(), "Shopping list has been created on server", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "Items added to list", Toast.LENGTH_LONG).show();
                Log.e("1337", response);
                optionIntent();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("1337", error.toString());
                Toast.makeText(getApplicationContext(), "Error connecting to server, please check internet and try again", Toast.LENGTH_LONG).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> postMap = new HashMap<>();
                postMap.put("companyname", comp_name);
                postMap.put("address", comp_addr);
                postMap.put("items", jsonList);
                return postMap;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    private void optionIntent() {
        Intent intent = new Intent(this, Main2Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
