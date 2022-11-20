package com.example.kukoslokos.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.kukoslokos.util.Service;

public class GuardarPeli extends AsyncTask<Void, Void, Void> {

    public static final int GUARDAR=1;
    public static final int ELMINIAR_GUARDADA=2;
    public static final int NOT_WORKING=3;


    int idUser, idPeli, accion;
    Context context;

    public GuardarPeli(int idUser, int idPeli, Context context, int accion){
        this.idPeli=idPeli;
        this.idUser=idUser;
        this.context=context;
        this.accion=accion;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (accion==GUARDAR)
            Service.guardarPeli(idUser, idPeli, context);
        else if (accion==ELMINIAR_GUARDADA)
            Service.eliminarGuardada(idUser, idPeli, context);
        return null;
    }
}
