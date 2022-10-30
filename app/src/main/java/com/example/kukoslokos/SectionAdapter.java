package com.example.kukoslokos;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kukoslokos.model.Seccion;

import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<SectionView> {

    List<Seccion> secciones;
    PeliculasAdapter.OnItemClickListener listener;

    public SectionAdapter(List<Seccion> secciones, PeliculasAdapter.OnItemClickListener listener){
        this.secciones=secciones;
        this.listener=listener;
    }
    @NonNull
    @Override
    public SectionView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.section, parent, false);
        return new SectionView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionView holder, int position) {
        Seccion seccion = secciones.get(position);

        holder.loadData(seccion.getTitulo(), seccion.getPeliculas(),listener);
    }


    @Override
    public int getItemCount() {
        return secciones.size();
    }
}
