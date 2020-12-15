package com.bonilla.proyecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AdaptadorRecomendado extends BaseAdapter{
    private Context context;
    private ArrayList<Hotel> listaItems;

    public AdaptadorRecomendado(Context context, ArrayList<Hotel> listaItems) {
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

        convertView = LayoutInflater.from(context).inflate(R.layout.lista_recomendados,null);
        ImageView img = (ImageView) convertView.findViewById(R.id.imgHotel2);
        TextView txtHotel = (TextView) convertView.findViewById((R.id.txtNombreHotel2));
        TextView txtDescripcion =  (TextView) convertView.findViewById(R.id.txtDescripcionHotel2);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.ratingbar);
        TextView txtVotos = (TextView) convertView.findViewById(R.id.txtVotos);
        TextView comentarios = (TextView) convertView.findViewById(R.id.txtComentario);

        new GetImageToURL(img).execute("https://proyectofinalhotel.000webhostapp.com/uploads/"+item.getNombreHotel()+".png");

        //img.setImageResource(item.getImg());
        txtHotel.setText(item.getNombreHotel());
        txtDescripcion.setText(item.getDescripcionHotel());
        ratingBar.setNumStars(5);
        ratingBar.setRating((float)item.getCalificacion());
        txtVotos.setText(String.valueOf(item.getVotos()));


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
}
