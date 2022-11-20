package com.example.kukoslokos.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.kukoslokos.util.Service;

public class GuardarPeli extends AsyncTask<Void, Void, Void> {

    int idUser, idPeli;
    Context context;

    public GuardarPeli(int idUser, int idPeli, Context context){
        this.idPeli=idPeli;
        this.idUser=idUser;
        this.context=context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Service.guardarPeli(idUser, idPeli, context);
        return null;
    }
}
