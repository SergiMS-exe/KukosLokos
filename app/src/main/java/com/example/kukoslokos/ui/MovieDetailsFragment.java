package com.example.kukoslokos.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.kukoslokos.MainRecyclerTab;
import com.example.kukoslokos.PeliculaView;
import com.example.kukoslokos.R;
import com.example.kukoslokos.model.Pelicula;
import com.example.kukoslokos.tasks.GetPeliById;
import com.example.kukoslokos.tasks.GetPeliculasGuardadas;
import com.example.kukoslokos.tasks.GuardarPeli;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ID_PELI = "idPeli";

    // TODO: Rename and change types of parameters
    private int idPeli;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MovieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailsFragment newInstance(int idPeli) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ID_PELI, idPeli);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idPeli = getArguments().getInt(ID_PELI);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        TextView titulo = (TextView) getView().findViewById(R.id.movieTitle);
        TextView sinopsis = (TextView) getView().findViewById(R.id.textSinopsis);
        ImageView poster = (ImageView) getView().findViewById(R.id.moviePoster);
        ImageView backdrop = (ImageView) getView().findViewById(R.id.movieBackdrop);
        Button btnGuardar = (Button) getView().findViewById(R.id.btnGuardarPeli);
        Button btnVerMas = (Button) getView().findViewById(R.id.btnVerMas);
        btnGuardar.setVisibility(View.INVISIBLE);

        try {
            GetPeliById peliById = new GetPeliById(idPeli);
            peliById.execute();
            Pelicula pelicula = peliById.get();

            titulo.setText(pelicula.getTitulo());
            sinopsis.setText(pelicula.getArgumento());
            sinopsis.setLines(3);

            Picasso.get().load(PeliculaView.BASE_URL_POSTER+pelicula.getPathPoster()).into(poster);

            Picasso.get().load(PeliculaView.BASE_URL_POSTER+pelicula.getPathBackdrop()).into(backdrop);


            SharedPreferences sharedPreferences = requireContext().getSharedPreferences(MainRecyclerTab.SHARED_PREFS, Context.MODE_PRIVATE);
            int userId = sharedPreferences.getInt(MainRecyclerTab.USER_ID_KEY, -1);
            int accion=GuardarPeli.NOT_WORKING;
            String textButton = "Guardar";
            if (userId!=-1) {//Comprobamos si el usuario esta registrado
                btnGuardar.setVisibility(View.VISIBLE);

                GetPeliculasGuardadas peliculasGuardadas = new GetPeliculasGuardadas(userId, getContext());
                peliculasGuardadas.execute();
                List<Pelicula> peliculaList = peliculasGuardadas.get();
                if (peliculaList.contains(pelicula)){ //Comprobamos si el usuario ya ha guardado la pelicula
                    btnGuardar.setText("Eliminar guardado");
                    accion=GuardarPeli.ELMINIAR_GUARDADA;
                } else {
                    accion = GuardarPeli.GUARDAR;
                    textButton="Eliminar guardado";
                }
            }

            int finalAccion = accion;
            String finalTextButton = textButton;
            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GuardarPeli guardarPelicula = new GuardarPeli(userId, pelicula.getId(), getContext(), finalAccion);
                    guardarPelicula.execute();

                    Animation animationGuardar = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                    btnGuardar.setAnimation(animationGuardar);

                    btnGuardar.setText(finalTextButton);
                }
            });

            btnVerMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (btnVerMas.getText().toString().equalsIgnoreCase("Ver más..."))
                    {
                        //129dp
                        sinopsis.setMaxLines(Integer.MAX_VALUE);
                        btnVerMas.setText("Ver menos");

                    }
                    else
                    {
                        sinopsis.setMaxLines(3);
                        btnVerMas.setText("Ver más...");
                    }
                }
            });

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
