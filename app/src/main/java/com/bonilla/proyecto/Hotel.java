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
<<<<<<< HEAD
    private float calificacion;
=======
    private int calificacion;
    private int votos;
>>>>>>> 24f1fd0170b75bbd8f4df07efe84e9dfe8889d1e

    public Hotel(int img, String nombreHotel, String descripcionHotel) {
        this.img =  img;
        this.nombreHotel = nombreHotel;
        this.descripcionHotel = descripcionHotel;
    }

<<<<<<< HEAD
    public Hotel(int img, String nombreHotel, String descripcionHotel, float calificacion){
=======
    public Hotel(int img, String nombreHotel, String descripcionHotel, int calificacion, int votos){
>>>>>>> 24f1fd0170b75bbd8f4df07efe84e9dfe8889d1e
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
