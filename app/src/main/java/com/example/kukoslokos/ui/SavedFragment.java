package com.example.kukoslokos.ui;

import static com.example.kukoslokos.MainRecyclerTab.PELICULA_SELECCIONADA;
import static com.example.kukoslokos.MainRecyclerTab.usuarioEnSesion;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kukoslokos.DetailsContent;
import com.example.kukoslokos.MainRecyclerTab;
import com.example.kukoslokos.PeliculasAdapter;
import com.example.kukoslokos.R;
import com.example.kukoslokos.model.Pelicula;
import com.example.kukoslokos.tasks.GetPeliById;
import com.example.kukoslokos.tasks.GetPeliculasGuardadas;
import com.example.kukoslokos.util.Service;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavedFragment extends Fragment {

    List<Pelicula> listaPeliculas;

    public SavedFragment() {
        // Required empty public constructor
    }

    protected void setListaPeliculas(List<Pelicula> listaPeliculas){
        this.listaPeliculas=listaPeliculas;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SavedFragment.
     */
    public static SavedFragment newInstance(List<Pelicula> listaPeliculas) {
        SavedFragment fragment = new SavedFragment();
        fragment.setListaPeliculas(listaPeliculas);
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
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.peliculasGuardadas);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        recyclerView.setLayoutManager(layoutManager);

        TextView titleSaved = (TextView) getView().findViewById(R.id.titleSaved);

        if (listaPeliculas!=null){
            titleSaved.setText("Resultado de la busqueda");
            titleSaved.setTextSize(30);
            showPeliculas(listaPeliculas);
            return;
        }

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(MainRecyclerTab.SHARED_PREFS, Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString(MainRecyclerTab.USER_ID_KEY, "");

        if (userId.equals("")){
            LoginFragment loginFragment = LoginFragment.newInstance(LoginFragment.SAVED);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, loginFragment).commit();
            return;
        }

        List<Pelicula> pelisFavs = new ArrayList<>();
        try {

            for (Integer peliId : usuarioEnSesion.getMoviesSaved()){
                GetPeliById peliById = new GetPeliById(peliId);
                peliById.execute();
                pelisFavs.add(peliById.get());
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        titleSaved.setText("Peliculas guardadas");
        titleSaved.setTextSize(30);
        showPeliculas(pelisFavs);

    }

    private void showPeliculas(List<Pelicula> pelis) {
        RecyclerView favsView = getView().findViewById(R.id.peliculasGuardadas);
        PeliculasAdapter.OnItemClickListener listener = new PeliculasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Pelicula peli) {
                clickOnItem(peli);
            }
        };

        favsView.setAdapter(new PeliculasAdapter(pelis, listener));
    }

    public void clickOnItem(Pelicula peli){
        //Paso el modo de apertura
        Intent intent = new Intent(getContext(), DetailsContent.class);
        intent.putExtra(PELICULA_SELECCIONADA, peli.getId());
        //Transacion de barrido
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
    }
}