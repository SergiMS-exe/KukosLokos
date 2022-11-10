package com.example.kukoslokos.tasks;

import android.os.AsyncTask;

import com.example.kukoslokos.model.Pelicula;
import com.example.kukoslokos.util.Service;

import java.util.List;

public class GetPelis extends AsyncTask<Void, Void, List<Pelicula>> {

    String categoria;

    public GetPelis(String categoria){
        this.categoria=categoria;
    }

    @Override
    protected List<Pelicula> doInBackground(Void... voids) {
        return Service.getPelis(categoria);
    }
}
