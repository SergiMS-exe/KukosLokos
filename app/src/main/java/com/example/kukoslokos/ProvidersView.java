package com.example.kukoslokos;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

public class ProvidersView extends RecyclerView.ViewHolder{

    ImageView logo;

    public ProvidersView(@NonNull View itemView) {
        super(itemView);

        logo = (ImageView) itemView.findViewById(R.id.providerLogo);
    }

    public void loadData(JsonObject provider, final ProvidersAdapter.OnItemClickListener listener){

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                JsonObject aux = (JsonObject)(provider.get("urls"));
                String url = aux.get("standard_web").getAsString();

                listener.onItemClick(url);
            }
        });
    }
}
