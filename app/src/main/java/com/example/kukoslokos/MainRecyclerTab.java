package com.example.kukoslokos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.kukoslokos.model.Pelicula;
import com.example.kukoslokos.model.Seccion;
import com.example.kukoslokos.tasks.GetPelisPopulares;
import com.example.kukoslokos.util.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainRecyclerTab extends AppCompatActivity {

    static final List<String> SECCIONES = new ArrayList<>(Arrays.asList("Novedades", "Populares", "Tendencias", "Para ti"));
    List<Pelicula> peliculaList;
    List<Seccion> seccionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_tab);

        cargarSecciones();
    }

    //Cargar secciones
    private void cargarSecciones(){
        peliculaList=cargarPeliculas();
        Log.i("Peliculas length",""+peliculaList.size());
        //Creamos batida de secciones estaticas
        seccionList=createSections();
        Log.i("Section length",""+seccionList.size());
        //obterner linearlayout
        RecyclerView seccionesView= findViewById(R.id.secctionList);
        seccionesView.setAdapter(new SectionAdapter(seccionList));

    }

    private List<Seccion> createSections() {
        List<Seccion> secciones = new ArrayList<Seccion>();

        secciones.add(new Seccion(SECCIONES.get(0), peliculaList));
        secciones.add(new Seccion(SECCIONES.get(1), peliculaList));
        secciones.add(new Seccion(SECCIONES.get(2), peliculaList));
        secciones.add(new Seccion(SECCIONES.get(3), peliculaList));

        return secciones;
    }

    private List<Pelicula> cargarPeliculas() {
        try {
            GetPelisPopulares populares = new GetPelisPopulares();
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