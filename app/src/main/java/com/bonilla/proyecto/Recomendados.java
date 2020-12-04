package com.bonilla.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Recomendados extends AppCompatActivity {

    Button btnInicio,btnFavoritos,btnConfiguracion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendados);
        btnInicio= findViewById(R.id.btnInicio);
        btnFavoritos= findViewById(R.id.btnFavoritos);
        btnConfiguracion= findViewById(R.id.btnConfiguracion);

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MenuPrincipal.class);
                startActivity(intent);
            }
        });

        btnFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Favoritos.class);
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