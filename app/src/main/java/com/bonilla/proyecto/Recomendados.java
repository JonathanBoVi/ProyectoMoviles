package com.bonilla.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Recomendados extends AppCompatActivity {

    Button btnInicio,btnFavoritos,btnConfiguracion;

    private ListView lista;
    private AdaptadorRecomendado adaptador2;
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

        lista = (ListView) findViewById(R.id.lblItem2);
        adaptador2 = new AdaptadorRecomendado(this, GetArrayItem());
        lista.setAdapter(adaptador2);

    }

    private ArrayList<Hotel> GetArrayItem(){
        ArrayList<Hotel> listaItem = new ArrayList<>();
        listaItem.add(new Hotel(R.drawable.casandina, "Casa Andina Select", "Federico Villarreal 115"));
        listaItem.add(new Hotel(R.drawable.sunec, "Sunec Hotel", "Manuel María Izaga 472"));
        listaItem.add(new Hotel(R.drawable.tumbasreales, "Tumbas Reales", "calle Andalucia 198 - 208 urb"));
        listaItem.add(new Hotel(R.drawable.winmeier, "Win Meier", "Av. Francisco Bolognesi 756"));
        return listaItem;
    }
}