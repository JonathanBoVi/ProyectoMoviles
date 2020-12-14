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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class registrarFavorito extends AppCompatActivity {

    private Button btnCancelar, btnGuardar;
    private RatingBar calif;
    private TextView nombre;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_favorito);

        btnCancelar = findViewById(R.id.btnCancelar);
        btnGuardar = findViewById(R.id.btnValorar);
        nombre=findViewById(R.id.txtNombreHotel);
        img=findViewById(R.id.txtImagen);

        calif = findViewById(R.id.txtEstrellas);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarFav("http://proyectofinalhotel.000webhostapp.com/registrarFav.php");
                Intent intent = new Intent(getApplicationContext(),Favoritos.class);
                startActivity(intent);
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DetalleHotel.class);
                startActivity(intent);
            }
        });

        new GetImageToURL().execute("https://proyectofinalhotel.000webhostapp.com/uploads/"+Utilidades.getNombreHotel()+".png");

        nombre.setText(Utilidades.getNombreHotel());
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

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private class GetImageToURL extends AsyncTask< String, Void, Bitmap> {
      /*  private ImageView img;

        public GetImageToURL(ImageView img) {
          this.img   = img;
        }*/

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
}