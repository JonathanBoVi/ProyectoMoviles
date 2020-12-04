package com.bonilla.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Favoritos extends AppCompatActivity {

    Button btnInicio,btnRecomendado,btnConfiguracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        btnInicio= findViewById(R.id.btnInicio);
        btnRecomendado= findViewById(R.id.btnRecomendado);
        btnConfiguracion= findViewById(R.id.btnConfiguracion);

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MenuPrincipal.class);
                startActivity(intent);
            }
        });

        btnRecomendado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Recomendados.class);
                startActivity(intent);
            }
        });

        btnConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Configuracion.class);
                startActivity(intent);
            }
        });


    }
}