package com.example.kukoslokos.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.kukoslokos.model.Pelicula;
import com.example.kukoslokos.model.Usuario;
import com.example.kukoslokos.tasks.GetPeliById;
import com.example.kukoslokos.util.Service;

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

    public long marcarPeliculaComoFav(int idPeli, int idUser) {
        open();
        ContentValues values = new ContentValues();

        values.put(MyDBHelper.COL_ID_FAVS, idPeli);
        values.put(MyDBHelper.COL_ID_USUARIO, idUser);
        values.put(MyDBHelper.COL_FECHA, System.currentTimeMillis());

        long insertId = database.insert(MyDBHelper.TABLA_FAVS, null, values);
        close();
        return insertId;
    }

    public List<Pelicula> getPeliculasFavs(int idUser){
        open();
        List<Pelicula> favs = new ArrayList<Pelicula>();

        String query = "SELECT * FROM "+MyDBHelper.TABLA_FAVS+" WHERE "+MyDBHelper.COL_ID_USUARIO+"="+idUser;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Pelicula peliFav = Service.getPeliById(cursor.getInt(0));
            favs.add(peliFav);
            cursor.moveToNext();
        }

        cursor.close();
        close();
        return favs;
    }

    public void eliminarFavPeli(int idPeli, int idUser) {
        open();
        String deleteQuery = "DELETE FROM "+MyDBHelper.TABLA_FAVS+" WHERE "+
                MyDBHelper.COL_ID_USUARIO+"="+idUser+" AND "+MyDBHelper.COL_ID_FAVS+" = "+idPeli;
        //database.rawQuery(deleteQuery, null);
        database.delete(MyDBHelper.TABLA_FAVS, MyDBHelper.COL_ID_FAVS+"=? AND "+
                MyDBHelper.COL_ID_USUARIO+"=?", new String[]{String.valueOf(idPeli), String.valueOf(idUser)});
        close();
    }
}
