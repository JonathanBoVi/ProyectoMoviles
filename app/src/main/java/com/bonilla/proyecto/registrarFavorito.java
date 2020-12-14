package com.bonilla.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class registrarFavorito extends AppCompatActivity {

    private Button btnCancelar, btnGuardar, btnSubirImagen;
    private RatingBar calif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_favorito);

        btnCancelar = findViewById(R.id.btnCancelar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnSubirImagen = findViewById(R.id.btnSubirImagen);
        calif = findViewById(R.id.txtEstrellas);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarFav("http://proyectofinalhotel.000webhostapp.com/registrarFav.php");
                Intent intent = new Intent(getApplicationContext(),DetalleHotel.class);
                startActivity(intent);
            }
        });
    }

    private void registrarFav(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xxd",error.toString());
                Toast.makeText(registrarFavorito.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String,String>();
                parametros.put("usuario",String.valueOf(Utilidades.getCorreo()));
                parametros.put("id_hotel",String.valueOf(Utilidades.getIdHotel()));
                parametros.put("calificacion",String.valueOf(calif.getRating()));
                return parametros;
            }
        };
    }
}