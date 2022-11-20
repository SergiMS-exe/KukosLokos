package com.example.kukoslokos.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.kukoslokos.datos.PeliculasDataSource;
import com.example.kukoslokos.model.Pelicula;
import com.example.kukoslokos.util.Service;

import java.util.List;

public class GetPeliculasGuardadas extends AsyncTask<Void, Void, List<Pelicula>> {

    int userId;
    Context context;

    public GetPeliculasGuardadas(int useId, Context context){
        this.userId=useId;
        this.context=context;
    }

    @Override
    public List<Pelicula> doInBackground(Void... voids) {
        PeliculasDataSource peliculasDataSource=new PeliculasDataSource(context);
        return peliculasDataSource.getPeliculasFavs(userId);
    }
}
