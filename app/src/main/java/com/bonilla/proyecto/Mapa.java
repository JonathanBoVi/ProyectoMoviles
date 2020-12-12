package com.bonilla.proyecto;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.CarrierConfigManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Mapa extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button TipoBtn;
    private Marker marcador;
    String hotel="1";
    double lat = 0.0;
    double lng = 0.0;

    Mapa datos;

    String url="https://proyectofinalhotel.000webhostapp.com/listarHoteles.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        TipoBtn = (Button) findViewById(R.id.btnSatelite);
        TipoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TipoBtn.getText().equals("SATELITE")){
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    TipoBtn.setText("NORMAL");
                } else{
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    TipoBtn.setText("SATELITE");
                }


            }
        });



    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
       // LatLng sydney = new LatLng(latitud, 151);
      //  mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mostrarHoteles("http://proyectofinalhotel.000webhostapp.com/listarHot.php");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.getUiSettings().setZoomControlsEnabled(true);

    }

    private void mostrarHoteles(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    if (!response.isEmpty()){
                        try {
                            JSONArray jsonArr = new JSONArray(response);
                            for (int i=0; i<jsonArr.length(); i++){
                                JSONObject objeto = jsonArr.getJSONObject(i);
                                String id = objeto.getString("id");
                                Double latitud = objeto.getDouble("latitud");
                                Double longitud = objeto.getDouble("longitud");
                                String nombre = objeto.getString("nombre");
                                Double color = objeto.getDouble("color");


                                mMap.addMarker(new MarkerOptions().position(new LatLng(latitud,longitud)).title(nombre).icon(BitmapDescriptorFactory.defaultMarker(Float.parseFloat(color.toString()))));
                            }
                        } catch (JSONException e) {
                            Log.e("catch","xd");
                        }
                        Log.e("MENSAJE",response);
                        //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-6.771769, -79.839506)));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.771769, -79.839506), 15.0f));


                    } else {
                    Toast.makeText(Mapa.this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Mapa.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String,String>();
                parametros.put("marcador",hotel);
                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}