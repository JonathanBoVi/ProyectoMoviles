package com.bonilla.proyecto;

public class Hotel {
    private int img;
    private String nombreHotel;
    private String descripcionHotel;

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
