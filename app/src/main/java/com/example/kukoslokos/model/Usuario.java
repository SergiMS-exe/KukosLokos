package com.example.kukoslokos.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Usuario {

    @SerializedName("_id")
    String id;
    String nombre;
    String apellidos;
    String email;
    String password;
    String nickName;
    @SerializedName("points")
    int puntos;
    List<Integer> moviesSaved;
    double lastRule;

    public Usuario(String id, String nombre, String nickName, String email, String password, int puntos, List<Integer> moviesSaved, double lastRule) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.puntos = puntos;
        this.nickName = nickName;
        this.moviesSaved = moviesSaved;
        this.lastRule = lastRule;
    }

    public double getLastRule() {
        return lastRule;
    }

    public void setLastRule(double lastRule) {
        this.lastRule = lastRule;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
