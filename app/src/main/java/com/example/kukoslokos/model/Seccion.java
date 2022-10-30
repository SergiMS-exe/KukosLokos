package com.example.kukoslokos.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.List;

public class Seccion {

    String titulo;
    List<Pelicula> peliculas;

    public Seccion(String titulo, List<Pelicula> peliculas) {
        this.titulo = titulo;
        this.peliculas = peliculas;
    }

    public String getTitulo() {
        return titulo;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }
}
