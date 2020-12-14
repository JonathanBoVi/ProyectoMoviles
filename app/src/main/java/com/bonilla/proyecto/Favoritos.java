package com.bonilla.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Favoritos extends AppCompatActivity {

    Button btnInicio,btnRecomendado,btnConfiguracion;
    private ListView lista;
    private Adaptador adaptador;
    int id;
    String nombre, descripcion;
    private ImageView imgHotel;
    private ArrayList<Hotel> listaItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        btnInicio= findViewById(R.id.btnInicio);
        btnRecomendado= findViewById(R.id.btnRecomendado);
        btnConfiguracion= findViewById(R.id.btnConfiguracion);
        imgHotel= findViewById(R.id.imgHotel);

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
        listarFavoritos("http://proyectofinalhotel.000webhostapp.com/listarFav.php");
      //  adaptador = new Adaptador(this, listaItem);
       // lista.setAdapter(adaptador);


    }

    private void listarFavoritos(String URL) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    if (!response.isEmpty()) {
                        try {

                            JSONArray jsonArr = new JSONArray(response);
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject objeto = jsonArr.getJSONObject(i);
                                id = objeto.getInt("id");
                                nombre = objeto.getString("nombre");
                                descripcion = objeto.getString("descripcion");
                                Hotel hotelito = new Hotel(R.drawable.winmeier, nombre, descripcion);
                                hotelito.setIdHotel(id);
                                listaItem.add(hotelito);


                                Log.e("response",listaItem.get(0).toString());


                            }

                            adaptador = new Adaptador(getApplicationContext(), listaItem);
                            lista.setAdapter(adaptador);

                        } catch (JSONException e) {
                            Log.e("respo",e.getMessage());
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(Favoritos.this, "Error en el response", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Favoritos.this, "Error en el lsitener", Toast.LENGTH_SHORT).show();
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

   /* private ArrayList<Hotel> GetArrayItem(){

        try {
            JSONArray jsonArr = new JSONArray();
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject objeto = jsonArr.getJSONObject(i);
                nombre = objeto.getString("h.nombre");
                descripcion = objeto.getString("h.descripcion");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<Hotel> listaItem = new ArrayList<>();
        listaItem.add(new Hotel(R.drawable.winmeier, "Win Meier", "Av. Francisco Bolognesi 756"));
        listaItem.add(new Hotel(R.drawable.lancelot, "Lancelot", "Alfonso Ugarte 639"));
        listaItem.add(new Hotel(R.drawable.mochiks, "Mochiks", "Tacna 615"));
        listaItem.add(new Hotel(R.drawable.tumbasreales, "Hotel Tumbas Reales", "calle Andalucia 198 - 208 urb"));
        listaItem.add(new Hotel(R.drawable.sunec, "Sunec Hotel Chiclayo", "Manuel María Izaga 472"));
        return listaItem;
    }*/


}