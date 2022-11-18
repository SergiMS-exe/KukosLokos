package com.example.kukoslokos.datos;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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


}
