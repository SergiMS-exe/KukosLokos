package com.example.kukoslokos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.kukoslokos.model.Pelicula;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainRecyclerTab extends AppCompatActivity {

    static final List<String> SECCIONES = new ArrayList<>(Arrays.asList("Novedades", "Populares", "Tendencias", "Para ti"));
    List<Pelicula> peliculaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_tab);


    }

    //Cargar secciones
    private void cargarSecciones(){
        //obterner linearlayoutç
        RecyclerView seccionList= findViewById(R.id.secctionList);
        seccionList.setAdapter(new SectionAdapter(SECCIONES, ));

    }
}