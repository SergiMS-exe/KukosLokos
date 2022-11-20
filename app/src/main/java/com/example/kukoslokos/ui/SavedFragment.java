package com.example.kukoslokos.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kukoslokos.MainRecyclerTab;
import com.example.kukoslokos.PeliculasAdapter;
import com.example.kukoslokos.R;
import com.example.kukoslokos.model.Pelicula;
import com.example.kukoslokos.tasks.GetPeliculasGuardadas;

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
    // TODO: Rename and change types and number of parameters
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

        if (listaPeliculas!=null){
            showPeliculas(listaPeliculas);
            return;
        }

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(MainRecyclerTab.SHARED_PREFS, Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt(MainRecyclerTab.USER_ID_KEY, -1);

        if (userId==-1){
            LoginFragment loginFragment = LoginFragment.newInstance(LoginFragment.SAVED);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, loginFragment).commit();
            return;
        }

        try {
            GetPeliculasGuardadas peliculasGuardadas = new GetPeliculasGuardadas(userId, getContext());
            List<Pelicula> pelisFavs = peliculasGuardadas.execute().get();

            showPeliculas(pelisFavs);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void showPeliculas(List<Pelicula> pelis) {
        RecyclerView favsView = getView().findViewById(R.id.peliculasGuardadas);
        PeliculasAdapter.OnItemClickListener listener = new PeliculasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Pelicula peli) {
                Log.i("listened", "Cambio de vista a DETALLES DE" + peli.getTitulo());
                //Creamos el framento de información
                MovieDetailsFragment movieFragment = MovieDetailsFragment.newInstance(peli.getId());
                getParentFragmentManager().beginTransaction().replace(R.id.mainFragment, movieFragment).commit();
            }
        };

        favsView.setAdapter(new PeliculasAdapter(pelis, listener));

        TextView noPelis = (TextView) getView().findViewById(R.id.noMovies);
        noPelis.setVisibility(View.INVISIBLE);
    }
}