package com.example.kukoslokos.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kukoslokos.PeliculasAdapter;
import com.example.kukoslokos.R;
import com.example.kukoslokos.SectionAdapter;
import com.example.kukoslokos.model.Pelicula;
import com.example.kukoslokos.model.Seccion;
import com.example.kukoslokos.tasks.GetPelis;

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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    static final Map<String, String> SECCIONES = Map.of("Novedades", "now_playing", "Populares","popular", "Tendencias","top_rated", "Proximamente", "upcoming");
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
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

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