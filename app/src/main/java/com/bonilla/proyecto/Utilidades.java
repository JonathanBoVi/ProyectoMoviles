package com.bonilla.proyecto;

public class Utilidades{
    private static String correo;
    private static int tipoSesion;
    private  static String nombreHotel;

    public static String getNombreHotel() {
        return nombreHotel;
    }

    public static void setNombreHotel(String nombreHotel) {
        Utilidades.nombreHotel = nombreHotel;
    }

    public static String getCorreo() {
        return correo;
    }

    public static void setCorreo(String correo) {
        Utilidades.correo = correo;
    }

    public static int getTipoSesion() {
        return tipoSesion;
    }

    public static void setTipoSesion(int tipoSesion) {
        Utilidades.tipoSesion = tipoSesion;
    }
}


