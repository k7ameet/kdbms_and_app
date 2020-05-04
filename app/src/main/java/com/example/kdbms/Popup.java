package com.example.kdbms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        // Int values for database
        int price_int;
        int weight_int;

        if(name.equals("") || price.equals("") || weight.equals("")){
            Toast.makeText(getApplicationContext(), "Please enter all required fields", Toast.LENGTH_SHORT).show();
        } else {
            price_int = Integer.parseInt(price);
            weight_int = Integer.parseInt(weight);
            Toast.makeText(getApplicationContext(), "Item added to shopping list", Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }
}
