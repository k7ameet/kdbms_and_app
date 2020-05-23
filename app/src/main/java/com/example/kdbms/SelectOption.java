package com.example.kdbms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SelectOption extends AppCompatActivity {

    private SurfaceView cameraS;
    private TextView text1;
    private Button addButton;
    private CameraSource Src;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private ToneGenerator toneGen;
    private String bcText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option);
        toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        cameraS = findViewById(R.id.camera_surface);
        text1 = findViewById(R.id.text_1);
        text1.setText("");
        addButton = findViewById(R.id.btnAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemButtonOnClick();
            }
        });
        initCamera();
    }

    private void initCamera() {

        BarcodeDetector bDet = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();

        Src = new CameraSource.Builder(this, bDet).setRequestedPreviewSize(1920, 1080).setAutoFocusEnabled(true).build();

        cameraS.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(SelectOption.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Src.start(cameraS.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(SelectOption.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) { Src.stop(); }
        });


        bDet.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {}

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> bcArray = detections.getDetectedItems();
                if (bcArray.size() != 0) {

                    text1.post(new Runnable() {

                        @Override
                        public void run() {

                            if (bcArray.valueAt(0).email != null) {
                                text1.removeCallbacks(null);
                                bcText = bcArray.valueAt(0).email.address;
                                text1.setText(bcText);
                                toneGen.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                            } else {

                                bcText = bcArray.valueAt(0).displayValue;
                                text1.setText(bcText);
                                toneGen.startTone(ToneGenerator.TONE_CDMA_PIP, 150);

                            }
                        }
                    });

                }
            }
        });
    }

    private  void addItemButtonOnClick() {
        if(text1.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please scan the item's barcode ", Toast.LENGTH_SHORT).show();
        } else{
            checkDatabaseForItem();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        getSupportActionBar().hide();
        Src.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().hide();
        initCamera();
    }

    private void checkDatabaseForItem(){
        String requestUrl = "https://us-central1-korean-export-dbms.cloudfunctions.net/app/api/items/exists";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("{\"message\":\"false\"}")){
                    Toast.makeText(getApplicationContext(), "Enter item details", Toast.LENGTH_SHORT).show();
                    text1.setText("");
                    nextPage();
                } else {
                    Toast.makeText(getApplicationContext(), "Error: Item is already in database", Toast.LENGTH_SHORT).show();
                    text1.setText("");
                }
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
                postMap.put("barcode", bcText);
                return postMap;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    private void nextPage(){
        Intent intent = new Intent(getApplicationContext(), Popup.class);
        intent.putExtra("NEW_ITEM_BARCODE", bcText);
        startActivity(intent);
    }
}
