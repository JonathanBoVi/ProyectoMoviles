package com.bonilla.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Recomendados extends AppCompatActivity {

    Button btnInicio,btnFavoritos,btnConfiguracion;

    private ListView lista;
    private AdaptadorRecomendado adaptador2;

    String nombre, descripcion;
    int calificacion;
    private ArrayList<Hotel> listaItem = new ArrayList<>();
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
        listarRecomendados("http://proyectofinalhotel.000webhostapp.com/listarRecomendados.php");
        /*adaptador2 = new AdaptadorRecomendado(this, GetArrayItem());
        lista.setAdapter(adaptador2);*/

    }

    private void listarRecomendados(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    try {

                        JSONArray jsonArr = new JSONArray(response);
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject objeto = jsonArr.getJSONObject(i);
                            nombre = objeto.getString("nombre");
                            descripcion = objeto.getString("descripcion");
                            calificacion = objeto.getInt("calificacion");
                            Hotel hotelito = new Hotel(R.drawable.winmeier, nombre, descripcion,calificacion);
                            listaItem.add(hotelito);


                            Log.e("response",listaItem.get(0).toString());


                        }

                        adaptador2 = new AdaptadorRecomendado(getApplicationContext(), listaItem);
                        lista.setAdapter(adaptador2);

                    } catch (JSONException e) {
                        Log.e("respo",e.getMessage());
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(Recomendados.this, "Error en el response", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Recomendados.this, "Error en el lsitener", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String,String>();
                parametros.put("usu",String.valueOf(Utilidades.getCorreo()));
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /*private ArrayList<Hotel> GetArrayItem(){
        ArrayList<Hotel> listaItem = new ArrayList<>();
        listaItem.add(new Hotel(R.drawable.casandina, "Casa Andina Select", "Federico Villarreal 115"));
        listaItem.add(new Hotel(R.drawable.sunec, "Sunec Hotel", "Manuel María Izaga 472"));
        listaItem.add(new Hotel(R.drawable.tumbasreales, "Tumbas Reales", "calle Andalucia 198 - 208 urb"));
        listaItem.add(new Hotel(R.drawable.winmeier, "Win Meier", "Av. Francisco Bolognesi 756"));
        return listaItem;
    }*/
}