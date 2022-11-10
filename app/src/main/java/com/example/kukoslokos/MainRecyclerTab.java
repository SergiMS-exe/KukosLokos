package com.example.kukoslokos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.kukoslokos.model.Pelicula;
import com.example.kukoslokos.model.Seccion;
import com.example.kukoslokos.tasks.GetPelis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainRecyclerTab extends AppCompatActivity {

    static final Map<String, String> SECCIONES = Map.of("Novedades", "now_playing", "Populares","popular", "Tendencias","top_rated", "Proximamente", "upcoming");
    List<Seccion> seccionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_tab);

        cargarSecciones();
    }

    //Cargar secciones
    private void cargarSecciones() {
        HashMap<String, List<Pelicula>> peliculasEnSecciones = new HashMap<String, List<Pelicula>>();
        for (String key: SECCIONES.keySet()){
            peliculasEnSecciones.put(key, cargarPeliculas(SECCIONES.get(key)));
        }
        //Creamos batida de secciones estaticas
        seccionList = createSections(peliculasEnSecciones);
        //obterner linearlayout
        RecyclerView seccionesView = findViewById(R.id.secctionList);
        PeliculasAdapter.OnItemClickListener listener = new PeliculasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Pelicula peli) {
                Log.i("listened", "Cambio de vista a DETALLES DE" + peli.getTitulo());
            }
        };
        seccionesView.setAdapter(new SectionAdapter(seccionList, listener));

    }

    private List<Seccion> createSections(HashMap<String, List<Pelicula>> peliculasEnSecciones) {
        List<Seccion> secciones = new ArrayList<Seccion>();

        for (String key : peliculasEnSecciones.keySet())
            secciones.add(new Seccion(key, peliculasEnSecciones.get(key)));

        return secciones;
    }

    private List<Pelicula> cargarPeliculas(String categoria) {
        try {
            GetPelis populares = new GetPelis(categoria);
            populares.execute();
            return populares.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}