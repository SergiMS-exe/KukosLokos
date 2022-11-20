package com.example.kukoslokos.tasks;

import android.os.AsyncTask;

import com.example.kukoslokos.model.Pelicula;
import com.example.kukoslokos.util.Service;

import java.util.List;

public class SearchPelis extends AsyncTask<Void, Void, List<Pelicula>> {

    String query;

    public SearchPelis(String query) {
        this.query=query;
    }


    @Override
    protected List<Pelicula> doInBackground(Void... voids) {
        return Service.searchPelis(query);
    }
}
