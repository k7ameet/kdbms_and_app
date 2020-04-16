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

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

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
        if(text1.getText().equals(null)) {
            Toast.makeText(getApplicationContext(), "Please scan the item's barcode ", Toast.LENGTH_SHORT).show();
        } else if (itemExistsInDatabase()){
            Toast.makeText(getApplicationContext(), "Item added to shopping list", Toast.LENGTH_SHORT).show();
            text1.setText("");
        } else{
            Toast.makeText(getApplicationContext(), "THIS PART IS INCOMPLETE", Toast.LENGTH_SHORT).show();
            text1.setText("");
        }
    }

    private boolean itemExistsInDatabase() {

        // HARDCODED FOR TESTING PURPOSES

        if(text1.getText().toString().equals("9316626102624")){
            return true;
        } return false;
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
}
