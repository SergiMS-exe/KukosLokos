package com.example.kukoslokos.model;

import java.util.List;

public class Serie {
    int id;
    String titulo;
    String argumento;
    String pathPoster;
    int[] generos;
    List<Temporada> temporadas;

    public Serie(int id, String titulo, String argumento, String pathPoster, int[] generos, List<Temporada> temporadas) {
        this.id=id;
        this.titulo = titulo;
        this.argumento = argumento;
        this.pathPoster = pathPoster;
        this.generos = generos;
        this.temporadas=temporadas;
    }

    public int getId(){
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getArgumento() {
        return argumento;
    }

    public String getPathPoster() {
        return pathPoster;
    }

    public int[] getGeneros() {
        return generos;
    }

    public List<Temporada> getTemporadas() {
        return temporadas;
    }
}
