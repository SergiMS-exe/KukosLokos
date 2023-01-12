package com.example.kukoslokos.ui;

import static com.example.kukoslokos.MainRecyclerTab.PELICULA_SELECCIONADA;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.kukoslokos.DetailsContent;
import com.example.kukoslokos.MainRecyclerTab;
import com.example.kukoslokos.PeliculasAdapter;
import com.example.kukoslokos.R;
import com.example.kukoslokos.SectionAdapter;
import com.example.kukoslokos.model.Pelicula;
import com.example.kukoslokos.model.Seccion;
import com.example.kukoslokos.tasks.GetGeneros;
import com.example.kukoslokos.tasks.GetPelis;
import com.example.kukoslokos.tasks.GetPelisByCategory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    static final Map<String, String> SECCIONES = Map.of("Novedades", "now_playing",
            "Populares","popular", "Tendencias","top_rated",
            "Proximamente", "upcoming");

    List<Seccion> seccionList;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
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
        RecyclerView seccionesView = getView().findViewById(R.id.secctionList);
        PeliculasAdapter.OnItemClickListener listener = new PeliculasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Pelicula peli) {
                Log.i("listened", "Cambio de vista a DETALLES DE" + peli.getTitulo());
                //Creamos el framento de información
                clickOnItem(peli);
            }
        };
        seccionesView.setAdapter(new SectionAdapter(seccionList, listener));

    }

    public void clickOnItem(Pelicula peli){
        //Paso el modo de apertura
        Intent intent = new Intent(getContext(), DetailsContent.class);
        intent.putExtra(PELICULA_SELECCIONADA, peli.getId());
        //Transacion de barrido
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
    }

    private List<Seccion> createSections(HashMap<String, List<Pelicula>> peliculasEnSecciones) {
        List<Seccion> secciones = new ArrayList<Seccion>();

        for (String key : peliculasEnSecciones.keySet())
            secciones.add(new Seccion(key, peliculasEnSecciones.get(key)));

        return secciones;
    }

    private List<Pelicula> cargarPeliculas(String categoria) {
        List<Pelicula> peliculasPop = new ArrayList<Pelicula>();
        GetPelis populares = new GetPelis(categoria);
        GetGeneros cargarGeneros = new GetGeneros();
        try {
            // está puesto "Todos los generos", esto
            // si no cargamos
            if(MainRecyclerTab.selectedItem == 0) {
                populares.execute();
                return populares.get();
            }else {
            // Filtrar por el genero seleccionado
                cargarGeneros.execute();
                HashMap<String, Integer> GENEROS = cargarGeneros.get();

            populares.execute();
            peliculasPop = populares.get();
            // Mirar la categoria filtada en el menú
                String generoActual = MainRecyclerTab.generosMain[MainRecyclerTab.selectedItem];
            // Buscar el valor en GENEROS
                int gerenoActual_id = GENEROS.get(generoActual);
            // Llamar a GetPelisByCategory pasarle la categoria a filtrar (con la lista de pelis)
                GetPelisByCategory pelisConGenero = new GetPelisByCategory(gerenoActual_id, peliculasPop);
                pelisConGenero.execute();
                List<Pelicula> wiwi = pelisConGenero.get();
                // Devolver la lista de peliculas filtradas
                return pelisConGenero.get();
            }
            //return peliculasPop;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}