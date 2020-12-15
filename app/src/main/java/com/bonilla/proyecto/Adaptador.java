package com.bonilla.proyecto;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Entity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Adaptador extends BaseAdapter{
        private Context context;
        private ArrayList<Hotel> listaItems;

        public Adaptador(Context context, ArrayList<Hotel> listaItems) {
            this.context = context;
            this.listaItems = listaItems;
        }

    @Override
    public int getCount() {
        return listaItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listaItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Hotel item = (Hotel) getItem(position);
        final Favoritos obj=new Favoritos();


        convertView = LayoutInflater.from(context).inflate(R.layout.lista_hotel,null);



        ImageView imgHotel = (ImageView) convertView.findViewById(R.id.imgHotel);
        TextView txtHotel = (TextView) convertView.findViewById(R.id.txtNombreHotel);
        TextView txtDescripcion = (TextView) convertView.findViewById(R.id.txtDescripcionHotel);
        Button btnEliminar = (Button) convertView.findViewById(R.id.btnEliminar);

        //img.setImageResource(item.getImg());
        //imgHotel.setImageBitmap(obj.getImage());
      //  new GetImageToURL(imgHotel).execute("https://proyectofinalhotel.000webhostapp.com/uploads/"+String.valueOf(Utilidades.getCorreo())+String.valueOf(item.getIdHotel())+".png");
       new GetImageToURL(imgHotel).execute("https://proyectofinalhotel.000webhostapp.com/uploads/"+item.getNombreHotel()+".png");

        txtHotel.setText(item.getNombreHotel());
        txtDescripcion.setText(item.getDescripcionHotel());

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarFavorito("https://proyectofinalhotel.000webhostapp.com/eliminarFavorito.php",item.getIdHotel());

                            }
        });


        Log.e("xdd",item.getNombreHotel());
        return convertView;
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

    public void eliminarFavorito(String URL, final int id_hotel){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("USU",response);
                if (response.equalsIgnoreCase("Hotel eliminado de favoritos")) {

                    Toast.makeText(context, "Hotel eliminado de favoritos", Toast.LENGTH_SHORT).show();



                   Intent intent=new Intent(context,Favoritos.class);
                    context.startActivity(intent);



                } else {
                    Toast.makeText(context, "Ocurrio un error intentalo de nuevo", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xxd",error.toString());
                Toast.makeText(context,error.toString(), Toast.LENGTH_SHORT).show();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parametros=new HashMap<String,String>();
                parametros.put("usuario",Utilidades.getCorreo());
                parametros.put("id_hotel",String.valueOf(id_hotel));
                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }



}
