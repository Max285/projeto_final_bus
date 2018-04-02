package com.ort.luiz.mybus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.app.Activity;

public class Onibus2Activity extends Activity {
    FirebaseDatabase database;
    DatabaseReference onibus2Ref;
    Button btnVoltar;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onibus1);

        database = FirebaseDatabase.getInstance();
        onibus2Ref = database.getReference("Onibus2");

        //Botao voltar
        btnVoltar = findViewById(R.id.btnVoltarID);
        btnVoltar.setOnClickListener((V)->{
            //Abre a pagina inicial
            startActivity(new Intent(this, FirstPageActivity.class));
        });

//Le do banco de dados
        onibus2Ref.child("QR").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TextView textView = findViewById(R.id.textID);
                String valor = dataSnapshot.child("Valor").getValue().toString();
                String hora = dataSnapshot.child("Hora").getValue().toString();

                textView.setText("Ponto atual: " + valor + " Hora: " + hora);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

