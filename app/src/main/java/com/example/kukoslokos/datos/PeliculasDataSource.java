package com.example.kukoslokos.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.kukoslokos.model.Pelicula;
import com.example.kukoslokos.model.Usuario;
import com.example.kukoslokos.tasks.GetPeliById;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PeliculasDataSource {

    private SQLiteDatabase database;
    private MyDBHelper dbHelper;


    public PeliculasDataSource(Context context) {
        dbHelper = new MyDBHelper(context, null, null, 1);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long marcarPeliculaComoFav(Pelicula peli, int idUser) {
        ContentValues values = new ContentValues();

        values.put(MyDBHelper.COL_ID_FAVS, peli.getId());
        values.put(MyDBHelper.COL_ID_USUARIO, idUser);
        values.put(MyDBHelper.COL_FECHA, System.currentTimeMillis());

        long insertId = database.insert(MyDBHelper.TABLA_FAVS, null, values);
        return insertId;
    }

    public List<Pelicula> getPeliculasFavs(int idUser){

        List<Pelicula> favs = new ArrayList<Pelicula>();

        String query = "SELECT * FROM "+MyDBHelper.TABLA_FAVS+" WHERE "+MyDBHelper.COL_ID_USUARIO+"="+idUser;

        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            try {
                GetPeliById peliById = new GetPeliById(cursor.getInt(0));
                peliById.execute();
                favs.add(peliById.get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cursor.moveToNext();
        }

        cursor.close();


        return favs;
    }
}
