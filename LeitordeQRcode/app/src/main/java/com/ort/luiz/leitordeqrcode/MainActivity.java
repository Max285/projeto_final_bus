package com.ort.luiz.leitordeqrcode;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    Button btnScan;
    private String onibus1;
    private String onibus2;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();

        btnScan =(Button) findViewById(R.id.btnScan);

        final Activity activity = this;

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Camera Scan");
                integrator.setCameraId(0);
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(result != null){
            if(result.getContents() != null){
                myRef = database.getReference("Onibus1");
                myRef.child("QR").setValue(result.getContents());
                myRef.child("Rota").child(result.getContents()).child("Position").setValue(1);

                if(onibus1 != null){
                    myRef.child("Rota").child(onibus1).child("Position").setValue(0);
                }

                onibus1 = result.getContents();
            } else{
                alert("Scan cancelado");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    
    private void alert(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}
