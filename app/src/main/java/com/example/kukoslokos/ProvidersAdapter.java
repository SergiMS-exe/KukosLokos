package com.example.kukoslokos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

public class ProvidersAdapter extends RecyclerView.Adapter<ProvidersView> {

    static Map<String, String> logosProviders = Map.of("itu", "https://www.justwatch.com/images/icon/190848813/s100/icon.webp",
            "ply", "https://images.justwatch.com/icon/169478387/s100/icon.webp",
            "amz", "https://www.justwatch.com/images/icon/52449861/s100/icon.webp",
            "wki", "https://images.justwatch.com/icon/128599720/s100/icon.webp",
            "msf", "https://images.justwatch.com/icon/820542/s100/icon.webp",
            "hbm", "https://images.justwatch.com/icon/285237061/s100/icon.webp",
            "mvs", "https://images.justwatch.com/icon/5985414/s100/icon.webp",
            "dnp", "https://www.justwatch.com/images/icon/147638351/s100/icon.webp",
            "nfx", "https://www.justwatch.com/images/icon/207360008/s100/icon.webp",
            "fip", "https://images.justwatch.com/icon/187161245/s100/icon.webp");

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
        return provider.size();
    }
}
