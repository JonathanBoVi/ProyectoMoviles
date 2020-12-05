package com.bonilla.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Favoritos extends AppCompatActivity {

    Button btnInicio,btnRecomendado,btnConfiguracion;
    private ListView lista;
    private Adaptador adaptador;

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

        lista = (ListView) findViewById(R.id.lblItem);
        adaptador = new Adaptador(this, GetArrayItem());
        lista.setAdapter(adaptador);

    }

    private ArrayList<Hotel> GetArrayItem(){
        ArrayList<Hotel> listaItem = new ArrayList<>();
        listaItem.add(new Hotel(R.drawable.winmeier, "Win Meier", "Av. Francisco Bolognesi 756"));
        listaItem.add(new Hotel(R.drawable.lancelot, "Lancelot", "Alfonso Ugarte 639"));
        listaItem.add(new Hotel(R.drawable.mochiks, "Mochiks", "Tacna 615"));

        return listaItem;
    }
}