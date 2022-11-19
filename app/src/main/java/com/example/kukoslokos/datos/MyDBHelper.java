package com.example.kukoslokos.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "kukoslokos.db";
    private static final int DB_VERSION = 1;


    //Tabla peliculas y columnas
    public static final String TABLA_FAVS = "tabla_favorito";

    public static final String COL_ID_FAVS = "id_favorito";
    public static final String COL_FECHA = "fecha";


    //Tabla usuario y columnas
    public static final String TABLA_USUARIO = "tabla_usuario";

    public static final String COL_ID_USUARIO = "id_usuario";
    public static final String COL_NOMBRE_USUARIO = "nombre_usuario";
    public static final String COL_APELLIDOS_USUARIO = "apellidos_usuario";
    public static final String COL_EMAIL_USUARIO = "email_usuario";
    public static final String COL_PASSWORD_USUARIO = "password_usuario";


    /**
     * Script de creacion de base de datos en sql
     */
    private static final String CREATE_TABLA_FAVS = "create table if not exists " + TABLA_FAVS
            + "( "+
            COL_ID_FAVS + " " + "integer, " +
            COL_ID_USUARIO + " " + "integer, "+
            COL_FECHA +" integer, "+
            "primary key ("+COL_ID_FAVS+", "+ COL_ID_USUARIO +"));";

    private static final String CREATE_TABLA_USUARIOS= "create table if not exists " + TABLA_USUARIO
            + "( "+
            COL_ID_USUARIO + " " + "integer primary key, " +
            COL_NOMBRE_USUARIO + " " + "text not null," +
            COL_APELLIDOS_USUARIO + " " + "text not null," +
            COL_EMAIL_USUARIO + " " + "text not null," +
            COL_PASSWORD_USUARIO + " " + "text not null" +
            " );";

    /**
     * Script borrado base de datos
     */
    private static final String DROP_FAVS = "DROP TABLE IF EXISTS " + TABLA_FAVS;
    private static final String DROP_USUARIOS = "DROP TABLE IF EXISTS " + TABLA_USUARIO;


    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLA_USUARIOS);
        sqLiteDatabase.execSQL(CREATE_TABLA_FAVS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_USUARIOS);
        sqLiteDatabase.execSQL(DROP_FAVS);
        onCreate(sqLiteDatabase);
    }
}
