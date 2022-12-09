package com.example.kukoslokos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kukoslokos.model.Pelicula;
import com.example.kukoslokos.tasks.GetPeliById;
import com.example.kukoslokos.tasks.GetPeliculasGuardadas;
import com.example.kukoslokos.tasks.GuardarPeli;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DetailsContent extends AppCompatActivity {

    Pelicula pelicula = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_content);

        //recogemos el id de la pelicula
        Intent intent = getIntent();
        int idPeli = intent.getIntExtra(MainRecyclerTab.PELICULA_SELECCIONADA, 0);

        TextView titulo = (TextView) findViewById(R.id.movieTitle);
        TextView sinopsis = (TextView) findViewById(R.id.textSinopsis);
        ImageView poster = (ImageView) findViewById(R.id.moviePoster);
        ImageView backdrop = (ImageView) findViewById(R.id.movieBackdrop);
        Button btnGuardar = (Button) findViewById(R.id.btnGuardarPeli);
        Button btnVerMas = (Button) findViewById(R.id.btnVerMas);
        btnGuardar.setVisibility(View.INVISIBLE);

        try {
            GetPeliById peliById = new GetPeliById(idPeli);
            peliById.execute();
            pelicula = peliById.get();

            titulo.setText(pelicula.getTitulo());
            sinopsis.setText(pelicula.getArgumento());
            sinopsis.setLines(3);

            Picasso.get().load(PeliculaView.BASE_URL_POSTER+pelicula.getPathPoster()).into(poster);

            Picasso.get().load(PeliculaView.BASE_URL_POSTER+pelicula.getPathBackdrop()).into(backdrop);


            SharedPreferences sharedPreferences = getSharedPreferences(MainRecyclerTab.SHARED_PREFS, Context.MODE_PRIVATE);
            int userId = sharedPreferences.getInt(MainRecyclerTab.USER_ID_KEY, -1);
            int accion= GuardarPeli.NOT_WORKING;
            String textButton = "Guardar";
            if (userId!=-1) {//Comprobamos si el usuario esta registrado
                btnGuardar.setVisibility(View.VISIBLE);

                GetPeliculasGuardadas peliculasGuardadas = new GetPeliculasGuardadas(userId, getApplicationContext());
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
                    GuardarPeli guardarPelicula = new GuardarPeli(userId, pelicula.getId(), getApplicationContext(), finalAccion);
                    guardarPelicula.execute();

                    Animation animationGuardar = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
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