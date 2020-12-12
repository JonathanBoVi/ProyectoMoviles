package com.bonilla.proyecto;

public class Utilidades{
    private static String correo;
    private static int tipoSesion;

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
