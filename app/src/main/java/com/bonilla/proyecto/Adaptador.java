package com.bonilla.proyecto;

import android.content.ClipData;
import android.content.Context;
import android.content.Entity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Adaptador extends BaseAdapter{
    private Context context;
    private ArrayList<Hotel> listaItems;
    private ImageView imgHotel;

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
        Hotel item = (Hotel) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.lista_hotel,null);

        new GetImageToURL().execute("https://proyectofinalhotel.000webhostapp.com/uploads/"+String.valueOf(Utilidades.getCorreo())+String.valueOf(item.getIdHotel())+".png");
        imgHotel = (ImageView) convertView.findViewById(R.id.imgHotel);
        TextView txtHotel = (TextView) convertView.findViewById(R.id.txtNombreHotel);
        TextView txtDescripcion = (TextView) convertView.findViewById(R.id.txtDescripcionHotel);

        //img.setImageResource(item.getImg());
        txtHotel.setText(item.getNombreHotel());
        txtDescripcion.setText(item.getDescripcionHotel());
        return convertView;
    }

    private class GetImageToURL extends AsyncTask< String, Void, Bitmap> {

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
                imgHotel.setImageBitmap(myBitMap);
            }

        }
    }


}
