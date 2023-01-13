package com.example.kukoslokos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kukoslokos.model.Pelicula;
import com.example.kukoslokos.model.Usuario;
import com.example.kukoslokos.tasks.GetPeliById;
import com.example.kukoslokos.tasks.GetPeliculasGuardadas;
import com.example.kukoslokos.tasks.GuardarPeli;
import com.example.kukoslokos.util.ApiUtil;
import com.example.kukoslokos.util.bodies.SaveMovieBody;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsContent extends AppCompatActivity {

    Pelicula pelicula = null;
    List<JsonObject> providers = null;

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

            //Obtenemos los proveedores de plataformas
            peticionObtenerProveedores(idPeli, pelicula.getTitulo());

            titulo.setText(pelicula.getTitulo());
            sinopsis.setText(pelicula.getArgumento());
            sinopsis.setLines(3);

            Picasso.get().load(PeliculaView.BASE_URL_POSTER+pelicula.getPathPoster()).into(poster);

            Picasso.get().load(PeliculaView.BASE_URL_POSTER+pelicula.getPathBackdrop()).into(backdrop);


            SharedPreferences sharedPreferences = getSharedPreferences(MainRecyclerTab.SHARED_PREFS, Context.MODE_PRIVATE);
            String userId = sharedPreferences.getString(MainRecyclerTab.USER_ID_KEY, "");

            String textButton = "Guardar";
            if (!sharedPreferences.getString(MainRecyclerTab.USER_ID_KEY, "").equals("")) {//Comprobamos si el usuario esta registrado
                btnGuardar.setVisibility(View.VISIBLE);

                if (MainRecyclerTab.usuarioEnSesion.getMoviesSaved().contains(pelicula.getId())) //Comprobamos si el usuario ya ha guardado la pelicula
                    btnGuardar.setText("Eliminar guardado");
                else
                    btnGuardar.setText("Guardar");
            }


            String finalTextButton = textButton;
            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    peticionGuardarPeli(userId, idPeli);

                    Animation animationGuardar = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                    btnGuardar.setAnimation(animationGuardar);
                    if (btnGuardar.getText().equals("Guardar"))
                        btnGuardar.setText("Eliminar guardado");
                    else if (btnGuardar.getText().equals("Eliminar guardado"))
                        btnGuardar.setText("Guardar");

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

    private void peticionObtenerProveedores(int idPeli, String titulo) {
        Call<JSONArray> call  = ApiUtil.getKukosApi().getProviders(idPeli, titulo);
        call.enqueue(new Callback<JSONArray>() {
            @Override
            public void onResponse(Call<JSONArray> call, Response<JSONArray> response) {
                switch (response.code()){
                    case 200:
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<JsonObject>>(){}.getType();
                        providers = gson.fromJson(response.body().toString(), listType);
                        break;
                    default:
                        call.cancel();
                        break;
                }
            }

            @Override
            public void onFailure(Call<JSONArray> call, Throwable t) {
                Log.e("providers - ", t.toString());
            }
        });
    }


    private void peticionGuardarPeli(String idUser, int idMovie) {
        Call<Usuario> call = ApiUtil.getKukosApi().saveMovie(new SaveMovieBody(idUser, idMovie));
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                switch (response.code()){
                    case 200:
                        MainRecyclerTab.usuarioEnSesion= response.body();
                        break;
                    default:
                        call.cancel();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("Login - error", t.toString());
            }
        });
    }

}