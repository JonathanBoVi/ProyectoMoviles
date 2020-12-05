package com.bonilla.proyecto;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.CarrierConfigManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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

import java.util.Map;

public class Mapa extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button TipoBtn;
    private Marker marcador;
    double lat = 0.0;
    double lng = 0.0;

    Mapa datos;

    String url="https://proyectofinalhotel.000webhostapp.com/listarHoteles.php";

    public void mostrardatosHotel(){
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject  jsonObject=new JSONObject(response);
                    String succes=jsonObject.getString("succes");

                    JSONArray jsonArray=jsonObject.getJSONArray("hotel");
                    if (succes.equals("1")){
                        for ( int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            String id=object.getString("id");
                            String nombre=object.getString("nombre");
                            String latitud=object.getString("latitud");
                            String longitud=object.getString("longitud");
                            String descripcion=object.getString("descripcion");
                            String valoracion=object.getString("valoracion");
                        }
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Mapa.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

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
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject  jsonObject=new JSONObject(response);
                    String succes=jsonObject.getString("succes");

                    JSONArray jsonArray=jsonObject.getJSONArray("hotel");
                    if (succes.equals("1")){
                        for ( int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            String id=object.getString("id");
                            String nombre=object.getString("nombre");
                            String latitud=object.getString("latitud");
                            String longitud=object.getString("longitud");
                            String descripcion=object.getString("descripcion");
                            String valoracion=object.getString("valoracion");


                        }
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Mapa.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        // Add a marker in Sydney and move the camera
       // LatLng sydney = new LatLng(latitud, 151);
      //  mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        LatLng Sunec = new LatLng( -6.773230, -79.840261);
        mMap.addMarker(new MarkerOptions().position(Sunec).title("Sunec Hotel Chiclayo").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Sunec));


        LatLng Muchiks = new LatLng( -6.774652, -79.838299);
        mMap.addMarker(new MarkerOptions().position(Muchiks).title("Hotel Muchiks").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Muchiks));

        LatLng WinMeier = new LatLng( -6.775787, -79.837841);
        mMap.addMarker(new MarkerOptions().position(WinMeier).title("Win Meier").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(WinMeier));

        LatLng Lancelot = new LatLng( -6.772530, -79.841751);
        mMap.addMarker(new MarkerOptions().position(Lancelot).title("Hostal Lancelot").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Lancelot));

        LatLng Gloria = new LatLng( -6.775024, -79.844015);
        mMap.addMarker(new MarkerOptions().position(Gloria).title("Gloria Plaza Hotel").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Gloria));

        LatLng Maracana = new LatLng( -6.775544, -79.845460);
        mMap.addMarker(new MarkerOptions().position(Maracana).title("Hostal Maracana").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Maracana));

        LatLng Boston = new LatLng( -6.771936, -79.849918);
        mMap.addMarker(new MarkerOptions().position(Boston).title("Hostal Boston").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Boston));

        LatLng CasaLuna = new LatLng( -6.769081, -79.848627);
        mMap.addMarker(new MarkerOptions().position(CasaLuna).title("Hostal Casa de la Luna").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(CasaLuna));

        LatLng Satelite = new LatLng( -6.773222, -79.865719);
        mMap.addMarker(new MarkerOptions().position(Satelite).title("Hostal Satelite").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Satelite));

        LatLng CasaAndina = new LatLng( -6.771501, -79.846295);
        mMap.addMarker(new MarkerOptions().position(CasaAndina).title("Casa Andina").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(CasaAndina));

        LatLng TumbasReales = new LatLng( -6.769081, -79.834507);
        mMap.addMarker(new MarkerOptions().position(TumbasReales).title("Hotel Tumbas Reales").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(TumbasReales));




        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.getUiSettings().setZoomControlsEnabled(true);

    }
}