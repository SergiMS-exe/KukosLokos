package com.example.kukoslokos.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "kukoslokos.db";
    private static final int DB_VERSION = 1;


    //Tabla peliculas y columnas
    public static final String TABLA_PELICULA = "tabla_pelicula";

    public static final String COL_ID_PELICULA = "id_pelicula";
    public static final String COL_ID_TMDB_PELICULA = "tmdb_id_pelicula";


    //Tabla usuario y columnas
    public static final String TABLA_USUARIO = "tabla_usuario";

    public static final String COL_ID_USUARIO = "id_usuario";
    public static final String COL_NOMBRE_USUARIO = "nombre_usuario";
    public static final String COL_APELLIDOS_USUARIO = "apellidos_usuario";
    public static final String COL_EMAIL_USUARIO = "email_usuario";
    public static final String COL_PASSWORD_USUARIO = "password_usuario";


    //Tabla pelicula-usuario
    public static final String TABLA_PELICULA_USUARIO = "tabla_pelicula_usuario";


    /**
     * Script de creacion de base de datos en sql
     */
    private static final String CREATE_TABLA_PELICULAS = "create table if not exists " + TABLA_PELICULA
            + "( "+
            COL_ID_PELICULA + " " + "integer primary key, " +
            COL_ID_TMDB_PELICULA + " " + "integer not null unique );";

    private static final String CREATE_TABLA_USUARIOS= "create table if not exists " + TABLA_USUARIO
            + "( "+
            COL_ID_USUARIO + " " + "integer primary key, " +
            COL_NOMBRE_USUARIO + " " + "text not null," +
            COL_APELLIDOS_USUARIO + " " + "text not null," +
            COL_EMAIL_USUARIO + " " + "text not null," +
            COL_PASSWORD_USUARIO + " " + "text not null," +
            " );";

    private static final String CREATE_TABLA_PELICULA_USUARIO= "create table if not exists " + TABLA_PELICULA_USUARIO
            + "( "+
            COL_ID_USUARIO + " " + "integer, " +
            COL_ID_PELICULA + " " + "integer, " +
            " primary key (" + COL_ID_USUARIO + ", " + COL_ID_PELICULA + ")"+
            " );";

    /**
     * Script borrado base de datos
     */
    private static final String DROP_PELICULAS = "DROP TABLE IF EXISTS " + TABLA_PELICULA;
    private static final String DROP_USUARIOS = "DROP TABLE IF EXISTS " + TABLA_USUARIO;
    private static final String DROP_PELICULA_USUARIOS = "DROP TABLE IF EXISTS " + TABLA_PELICULA_USUARIO;


    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLA_PELICULAS);
        sqLiteDatabase.execSQL(CREATE_TABLA_PELICULA_USUARIO);
        sqLiteDatabase.execSQL(CREATE_TABLA_USUARIOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_PELICULAS);
        sqLiteDatabase.execSQL(DROP_PELICULA_USUARIOS);
        sqLiteDatabase.execSQL(DROP_USUARIOS);
        onCreate(sqLiteDatabase);
    }
}
