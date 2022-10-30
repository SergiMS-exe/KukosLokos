package com.example.kukoslokos;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kukoslokos.model.Pelicula;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SectionView extends RecyclerView.ViewHolder {

    RecyclerView peliculas;
    TextView titulo;

    public SectionView(@NonNull View itemView) {
        super(itemView);

        titulo=(TextView) itemView.findViewById(R.id.tituloSeccion);
        peliculas=(RecyclerView) itemView.findViewById(R.id.listPeliculas);

    }
    public void loadData(String tituloSeccion, List<Pelicula> pelis, PeliculasAdapter.OnItemClickListener listener){
        titulo.setText(tituloSeccion);
        peliculas.setLayoutManager(new LinearLayoutManager(peliculas.getContext(),LinearLayoutManager.HORIZONTAL,false));
        peliculas.setAdapter(new PeliculasAdapter(pelis, listener));
    }
}
