package com.example.kukoslokos;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kukoslokos.model.Pelicula;
import com.squareup.picasso.Picasso;

public class PeliculaView extends RecyclerView.ViewHolder {

    public static final String BASE_URL_POSTER="https://image.tmdb.org/t/p/w500";

    ImageView poster;
    TextView titulo;

    public PeliculaView(@NonNull View itemView) {
        super(itemView);

        titulo=(TextView) itemView.findViewById(R.id.titulo);
        poster=(ImageView) itemView.findViewById(R.id.poster);

    }
    public void loadData(Pelicula peli, final PeliculasAdapter.OnItemClickListener listener){
        titulo.setText(peli.getTitulo());
        Picasso.get()
                .load(BASE_URL_POSTER+peli.getPathPoster()).into(poster);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(peli);
            }
        });
    }
}
