package com.example.kukoslokos;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kukoslokos.model.Pelicula;
import com.squareup.picasso.Picasso;

public class SectionView extends RecyclerView.ViewHolder {

    RecyclerView peliculas;
    TextView titulo;

    public SectionView(@NonNull View itemView) {
        super(itemView);

        titulo=(TextView) itemView.findViewById(R.id.tituloSeccion);
        peliculas=(RecyclerView) itemView.findViewById(R.id.listPeliculas);

    }
    public void loadData(String tituloSeccion, PeliculasAdapter pelis){
        titulo.setText(tituloSeccion);
        peliculas.setAdapter(pelis);
    }
}
