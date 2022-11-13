package com.example.kukoslokos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.kukoslokos.model.Pelicula;
import com.example.kukoslokos.model.Seccion;
import com.example.kukoslokos.tasks.GetPelis;
import com.example.kukoslokos.ui.HomeFragment;
import com.example.kukoslokos.ui.ProfileFragment;
import com.example.kukoslokos.ui.SavedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainRecyclerTab extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_tab);

        //Gestion de la barra de navegacion
        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        navView.setOnNavigationItemSelectedListener(navBarListener);
        navView.setSelectedItemId(R.id.home);

        int i=0;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navBarListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.home:
                    //Creamos el framento de información
                    HomeFragment homeFragment = new HomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();

                    return true;

                case R.id.saved:
                    SavedFragment savedFragment = new SavedFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, savedFragment).commit();


                    return true;

                case R.id.profile:

                    ProfileFragment profileFragment = new ProfileFragment();

                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, profileFragment).commit();

                    return true;

                default:
                    throw new IllegalStateException("Unexpected value: " + item.getItemId());

            }

        }

    };



}