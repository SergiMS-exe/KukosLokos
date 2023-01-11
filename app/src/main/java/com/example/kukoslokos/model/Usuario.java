package com.example.kukoslokos.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Usuario {

    @SerializedName("_id")
    int id;
    String nombre;
    String apellidos;
    String email;
    String password;
    @SerializedName("points")
    int puntos;
    List<Integer> moviesSaved;

    public Usuario(int id, String nombre, String apellidos, String email, String password) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public List<Integer> getMoviesSaved() {
        return moviesSaved;
    }

    public void setMoviesSaved(List<Integer> moviesSaved) {
        this.moviesSaved = moviesSaved;
    }

    public Usuario(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
