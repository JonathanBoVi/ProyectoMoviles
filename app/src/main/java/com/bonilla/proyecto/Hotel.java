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

    public Hotel(int img, String nombreHotel, String descripcionHotel) {
        this.img =  img;
        this.nombreHotel = nombreHotel;
        this.descripcionHotel = descripcionHotel;
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
