package com.bonilla.proyecto;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;

public class Hotel {
    private int img;
    private String nombreHotel;
    private String descripcionHotel;

    String url="https://proyectofinalhotel.000webhostapp.com/listarHoteles.php";

    public void mostrardatosHotel(){
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
    }

    public Hotel(int img, String titulo, String contenido) {
        this.img =  img;
        this.nombreHotel = titulo;
        this.descripcionHotel = contenido;
    }

    public int getImg() {
        return img;
    }

    public String getNombreHotel() {
        return nombreHotel;
    }

    public String getDescripcionHotel() {
        return descripcionHotel;
    }
}
