package com.example.kdbms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Popup extends AppCompatActivity {

    EditText Name;
    EditText Price;
    EditText Weight;
    EditText Desc;
    TextView Barcode;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        Barcode = findViewById(R.id.barcodeDisplay);
        Barcode.setText(getIntent().getStringExtra("NEW_ITEM_BARCODE"));
        Name = findViewById(R.id.inputName);
        Name.setText("");
        Price = findViewById(R.id.inputPrice);
        Price.setText("");
        Weight = findViewById(R.id.inputWeight);
        Desc = findViewById(R.id.inputDesc);
        Desc.setText("");
        button = findViewById(R.id.newItemButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonCLick();
            }
        });

    }

    private void buttonCLick(){
        String name = Name.getText().toString();
        String price = Price.getText().toString();
        String weight = Weight.getText().toString();
        String desc = Desc.getText().toString();

        if(name.equals("") || price.equals("") || weight.equals("")){
            Toast.makeText(getApplicationContext(), "Please enter all required fields", Toast.LENGTH_SHORT).show();
        } else {
            sendToServer(name, desc, price, weight);
        }
    }

    private void sendToServer(final String name, final String desc, final String price, final String weight){
        String requestUrl = "http://ptsv2.com/t/1lp3f-1588607923/post";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Item added to shopping list", Toast.LENGTH_SHORT).show();
                finishPopup();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error connecting to server, please check internet and try again", Toast.LENGTH_LONG).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> postMap = new HashMap<>();
                postMap.put("barcode", Barcode.getText().toString());
                postMap.put("name", name);
                postMap.put("price", price);
                postMap.put("weight", weight);
                postMap.put("desc", desc);
                return postMap;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    private void finishPopup(){
        this.finish();
    }
}
