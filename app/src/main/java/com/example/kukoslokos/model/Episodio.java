package com.example.kukoslokos.model;

import java.util.Date;

public class Episodio {
    int id;
    Date fechaEmision;
    String nombre;
    String argumento;
    int numEpisodio;

    public Episodio(int id, Date fechaEmision, String nombre, String argumento, int numEpisodio) {
        this.id = id;
        this.fechaEmision = fechaEmision;
        this.nombre = nombre;
        this.argumento = argumento;
        this.numEpisodio = numEpisodio;
    }

    public int getId() {
        return id;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public String getNombre() {
        return nombre;
    }

    public String getArgumento() {
        return argumento;
    }

    public int getNumEpisodio() {
        return numEpisodio;
    }
}
