package com.bonilla.proyecto;

import android.media.Rating;
import android.widget.RatingBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;

public class Hotel {
    private int idHotel;
    private int img;
    private String nombreHotel;
    private String descripcionHotel;
    private float calificacion;
    private int votos;

    public Hotel(int img, String nombreHotel, String descripcionHotel) {
        this.img =  img;
        this.nombreHotel = nombreHotel;
        this.descripcionHotel = descripcionHotel;
    }

    public Hotel(int img, String nombreHotel, String descripcionHotel, float calificacion, int votos){
        this.img = img;
        this.nombreHotel = nombreHotel;
        this.descripcionHotel = descripcionHotel;
        this.calificacion = calificacion;
        this.votos = votos;
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

    public int getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }
}
