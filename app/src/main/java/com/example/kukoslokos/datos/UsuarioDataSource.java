package com.example.kukoslokos.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.kukoslokos.model.Pelicula;
import com.example.kukoslokos.model.Usuario;
import com.example.kukoslokos.tasks.GetPeliById;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UsuarioDataSource {

    private SQLiteDatabase database;
    private MyDBHelper dbHelper;


    public UsuarioDataSource(Context context) {
        dbHelper = new MyDBHelper(context, null, null, 1);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long createUsuario(Usuario user) {
        open();
        ContentValues values = new ContentValues();

        values.put(MyDBHelper.COL_ID_USUARIO, user.getId());
        values.put(MyDBHelper.COL_NOMBRE_USUARIO, user.getNombre());
        values.put(MyDBHelper.COL_APELLIDOS_USUARIO, user.getApellidos());
        values.put(MyDBHelper.COL_EMAIL_USUARIO, user.getEmail());
        values.put(MyDBHelper.COL_PASSWORD_USUARIO, user.getPassword());

        long insertId = database.insert(MyDBHelper.TABLA_USUARIO, null, values);
        close();
        return insertId;
    }

    /**public Usuario login(String email, String password){
        open();
        Usuario user = new Usuario();

        if (email.equals("") && password.equals("")){
            user = new Usuario(1, "Prueba", "Prueba", "Prueba@uniovi.es", "Prueba");
            createUsuario(user);
            return user;
        }
        String query = "SELECT * FROM " +MyDBHelper.TABLA_USUARIO+" WHERE "+MyDBHelper.COL_EMAIL_USUARIO+"="+email+" and "+MyDBHelper.COL_PASSWORD_USUARIO+"="+password;

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            user.setId(cursor.getInt(0));
            user.setNombre(cursor.getString(1));
            user.setApellidos(cursor.getString(2));
            user.setEmail(cursor.getString(3));
            user.setPassword(cursor.getString(4));
            cursor.moveToNext();
        }

        cursor.close();
        close();
        return user;
    }

    public Usuario getUserById(int id){
        open();
        Usuario user = new Usuario();

        String query = "SELECT * FROM "+MyDBHelper.TABLA_USUARIO+" WHERE "+MyDBHelper.COL_ID_USUARIO+"="+id;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            user.setId(cursor.getInt(0));
            user.setNombre(cursor.getString(1));
            user.setApellidos(cursor.getString(2));
            user.setEmail(cursor.getString(3));
            user.setPassword(cursor.getString(4));
            cursor.moveToNext();
        }

        cursor.close();
        close();
        return user;

    }

    public Usuario registrar(String nombre, String apellidos, String nickname, String email, String password) {
        open();
        String lastIdQuery = "SELECT max("+MyDBHelper.COL_ID_USUARIO+") FROM "+MyDBHelper.TABLA_USUARIO;
        Cursor cursor = database.rawQuery(lastIdQuery, null);
        cursor.moveToFirst();

        int lastId=-1;
        while (!cursor.isAfterLast()){
            lastId=cursor.getInt(0);
        }

        Usuario usuario = new Usuario(lastId+1, nombre,apellidos,email,password);
        createUsuario(usuario);

        close();
        return usuario;
    }*/
}
