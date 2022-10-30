package com.example.kukoslokos.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Pelicula implements Parcelable {

    int id;
    String titulo;
    String argumento;
    String pathPoster;

    List<String> generos;

    public Pelicula(int id, String titulo, String argumento, String pathPoster, List<String> generos) {
        this.id=id;
        this.titulo = titulo;
        this.argumento = argumento;
        this.pathPoster = pathPoster;
        this.generos = generos;
    }

    protected Pelicula(Parcel in){
        id=in.readInt();
        titulo=in.readString();
        argumento=in.readString();
        pathPoster=in.readString();
        generos= in.readArrayList(String.class.getClassLoader());
    }



    public int getId(){
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getArgumento() {
        return argumento;
    }

    public String getPathPoster() {
        return pathPoster;
    }

    public List<String> getGeneros() {
        return generos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(titulo);
        dest.writeString(argumento);
        dest.writeString(pathPoster);
        dest.writeStringList(generos);
    }

    public static final Creator<Pelicula> CREATOR = new Creator<Pelicula>() {
        @Override
        public Pelicula createFromParcel(Parcel in) {
            return new Pelicula(in);
        }

        @Override
        public Pelicula[] newArray(int size) {
            return new Pelicula[size];
        }
    };
}
