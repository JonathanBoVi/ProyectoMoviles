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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DetalleHotel extends AppCompatActivity {

    TextView nombreHotel,direc,descrip;
    RatingBar estre;
    ImageView img;
    Button btnValorar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_hotel);

        mostrarHoteles("https://proyectofinalhotel.000webhostapp.com/listarDetalleHotel.php");
        nombreHotel=findViewById(R.id.txtNombreHotel);
        direc=findViewById(R.id.txtDireccion);
        descrip=findViewById(R.id.txtDescripcion);
        estre=findViewById(R.id.txtEstrellas);
        nombreHotel.setText(Utilidades.getNombreHotel());

        new GetImageToURL(img).execute("https://proyectofinalhotel.000webhostapp.com/uploads/"+String.valueOf(nombreHotel)+".png");
        img=findViewById(R.id.txtImagen);

        btnValorar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),registrarFavorito.class);
                startActivity(intent);
            }
        });
    }

    private class GetImageToURL extends AsyncTask< String, Void, Bitmap> {
        private ImageView img;

        public GetImageToURL(ImageView img) {
            this.img = img;
        }

        @Override
        protected Bitmap doInBackground(String...params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                // Log exception
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap myBitMap) {
            if (myBitMap!=null){
                img.setImageBitmap(myBitMap);
            }

        }
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
                            int idHot = objeto.getInt("id");
                            String direccion = objeto.getString("descripcion");
                            int valoracion = objeto.getInt("valoracion");
                            String descrpcion = objeto.getString("descrip");

                            Utilidades.setIdHotel(idHot);
                            direc.setText(direccion);
                            descrip.setText(descrpcion);
                            estre.setRating(valoracion);

                        }
                    } catch (JSONException e) {
                        Log.e("catch","xd");
                    }
                    Log.e("MENSAJE",response);

                } else {
                    Toast.makeText(DetalleHotel.this, "Hotel no registrado", Toast.LENGTH_SHORT).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetalleHotel.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String,String>();
                parametros.put("nom",Utilidades.getNombreHotel());
                return parametros;
            }


        };


        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }


}