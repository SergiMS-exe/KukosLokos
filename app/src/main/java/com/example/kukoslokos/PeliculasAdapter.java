package com.example.kukoslokos;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kukoslokos.model.Pelicula;

import java.util.List;

public class PeliculasAdapter extends RecyclerView.Adapter<PeliculaView>{

    private List<Pelicula> peliculas;
    private final OnItemClickListener listener;

    public PeliculasAdapter(List<Pelicula> listaPeli, OnItemClickListener listener) {
        this.peliculas = listaPeli;
        this.listener = listener;
    }

    // Interfaz para manejar el evento click sobre un elemento
    public interface OnItemClickListener {
        void onItemClick(Pelicula item);
    }


    @NonNull
    @Override
    public PeliculaView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Creamos la vista con el layout para un elemento
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.peli_item, parent, false);
        return new PeliculaView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PeliculaView holder, int position) {
        Pelicula peli = peliculas.get(position);
        Log.i("pelis list", getItemCount()+"");
        holder.loadData(peli, listener);
    }


    @Override
    public int getItemCount() {
        return peliculas.size();
    }


}
