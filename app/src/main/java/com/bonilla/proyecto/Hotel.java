package com.bonilla.proyecto;

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
