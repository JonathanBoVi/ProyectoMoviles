package com.bonilla.proyecto;

import android.content.ClipData;
import android.content.Context;
import android.content.Entity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

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
        Hotel item = (Hotel) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.lista_hotel,null);
        ImageView img = (ImageView) convertView.findViewById(R.id.imgHotel);
        TextView txtHotel = (TextView) convertView.findViewById(R.id.txtNombreHotel);
        TextView txtDescripcion = (TextView) convertView.findViewById(R.id.txtDescripcionHotel);

        img.setImageResource(item.getImg());
        txtHotel.setText(item.getNombreHotel());
        txtDescripcion.setText(item.getDescripcionHotel());
        return convertView;
    }
}
