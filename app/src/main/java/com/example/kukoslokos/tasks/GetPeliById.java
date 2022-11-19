package com.example.kukoslokos.tasks;

import android.os.AsyncTask;

import com.example.kukoslokos.model.Pelicula;
import com.example.kukoslokos.util.Service;

import java.util.List;

public class GetPeliById extends AsyncTask<Void, Void, Pelicula> {

    int id;

    public GetPeliById(int id) {
        this.id=id;
    }

    @Override
    protected Pelicula doInBackground(Void... voids) {
        return Service.getPeliById(id);
    }
}
