package com.example.kukoslokos.model;

import java.util.List;

public class Temporada {
    int id;
    String nombre;
    int numTemporada;
    String diaEstreno;
    String argumento;
    String posterPath;
    List<Episodio> episodios;

    public Temporada(int id, String nombre, int numTemporada, String diaEstreno, String argumento, String posterPath, List<Episodio> episodios) {
        this.id=id;
        this.nombre = nombre;
        this.numTemporada = numTemporada;
        this.diaEstreno = diaEstreno;
        this.argumento = argumento;
        this.posterPath = posterPath;
        this.episodios = episodios;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumTemporada() {
        return numTemporada;
    }

    public String getDiaEstreno() {
        return diaEstreno;
    }

    public String getArgumento() {
        return argumento;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }
}
