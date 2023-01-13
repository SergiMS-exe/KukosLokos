package com.example.kukoslokos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

import java.util.List;

public class ProvidersAdapter extends RecyclerView.Adapter<ProvidersView> {

    private List<JsonObject> provider;
    private final ProvidersAdapter.OnItemClickListener listener;

    public ProvidersAdapter(List<JsonObject> provider, OnItemClickListener listener) {
        this.provider = provider;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String ruta);
    }

    @NonNull
    @Override
    public ProvidersView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.provider_item, parent, false);
        return new ProvidersView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProvidersView holder, int position) {
        holder.loadData(provider.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
