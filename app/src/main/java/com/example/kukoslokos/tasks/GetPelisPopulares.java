package com.example.kukoslokos.tasks;

import android.os.AsyncTask;

import com.example.kukoslokos.model.Pelicula;
import com.example.kukoslokos.util.Service;

import java.net.URL;
import java.util.List;

public class GetPelisPopulares extends AsyncTask<Void, Void, List<Pelicula>> {


    @Override
    protected List<Pelicula> doInBackground(Void... voids) {
        return Service.getPelisPopulares();
    }
}
