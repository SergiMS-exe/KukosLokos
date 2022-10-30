package com.example.kukoslokos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter {

    List<String> secciones;

    public SectionAdapter(List<String> secciones){
        this.secciones=secciones;
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

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
