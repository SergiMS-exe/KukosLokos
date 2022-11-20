package com.example.kukoslokos.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Objects;

public class Pelicula implements Parcelable {

    int id;
    String titulo;
    String argumento;
    String pathPoster;
    String pathBackdrop;

    List<String> generos;

    public Pelicula(){}

    public Pelicula(int id, String titulo, String argumento, String pathPoster, String pathBackdrop, List<String> generos) {
        this.id=id;
        this.titulo = titulo;
        this.argumento = argumento;
        this.pathPoster = pathPoster;
        this.pathBackdrop = pathBackdrop;
        this.generos = generos;
    }

    protected Pelicula(Parcel in){
        id=in.readInt();
        titulo=in.readString();
        argumento=in.readString();
        pathPoster=in.readString();
        pathBackdrop=in.readString();
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

    public String getPathBackdrop() {
        return pathBackdrop;
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
        dest.writeString(pathBackdrop);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pelicula pelicula = (Pelicula) o;
        return id == pelicula.id && Objects.equals(titulo, pelicula.titulo) && Objects.equals(argumento, pelicula.argumento) && Objects.equals(pathPoster, pelicula.pathPoster) && Objects.equals(pathBackdrop, pelicula.pathBackdrop) && Objects.equals(generos, pelicula.generos);
    }

}
