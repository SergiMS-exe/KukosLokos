package com.example.kukoslokos.tasks;

import android.os.AsyncTask;

import com.example.kukoslokos.model.Pelicula;
import com.example.kukoslokos.util.Service;

import java.util.List;

public class GetPelisByCategory extends AsyncTask<Void, Void, List<Pelicula>> {

    int genero_id;
    List<Pelicula> peliculasAFiltrar;

    public GetPelisByCategory(int genero_id, List<Pelicula> peliculasAFiltrar) {
        this.genero_id = genero_id;
        this.peliculasAFiltrar = peliculasAFiltrar;
    }

    @Override
    protected List<Pelicula> doInBackground(Void... voids) {
        return Service.getMoviesByGenero(genero_id, peliculasAFiltrar);
    }
}