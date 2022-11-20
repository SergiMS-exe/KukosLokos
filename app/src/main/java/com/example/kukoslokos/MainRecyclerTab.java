package com.example.kukoslokos;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.kukoslokos.model.Pelicula;
import com.example.kukoslokos.tasks.SearchPelis;
import com.example.kukoslokos.ui.HomeFragment;
import com.example.kukoslokos.ui.ProfileFragment;
import com.example.kukoslokos.ui.SavedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainRecyclerTab extends AppCompatActivity {

    // KEYS
    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";
    // key for storing user id.
    public static final String USER_ID_KEY = "user_id_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_tab);

        //Gestion de la barra de navegacion
        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        navView.setOnNavigationItemSelectedListener(navBarListener);
        navView.setSelectedItemId(R.id.home);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navBarListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            getSupportActionBar().hide();
            switch (item.getItemId()) {
                case R.id.home:
                    //Creamos el framento de información
                    HomeFragment homeFragment = new HomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, homeFragment).commit();
                    getSupportActionBar().show();
                    return true;

                case R.id.saved:
                    SavedFragment savedFragment = new SavedFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, savedFragment).commit();

                    return true;

                case R.id.profile:
                    ProfileFragment profileFragment = new ProfileFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, profileFragment).commit();

                    return true;

                default:
                    throw new IllegalStateException("Unexpected value: " + item.getItemId());

            }

        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_up, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Escriba para buscar");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                List<Pelicula> peliculas = null;
                try {
                    peliculas = buscarPeliculas(s);
                    SavedFragment savedFragment = SavedFragment.newInstance(peliculas);
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, savedFragment).commit();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                HomeFragment homeFragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, homeFragment).commit();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private List<Pelicula> buscarPeliculas(String s) throws ExecutionException, InterruptedException {
        SearchPelis searchPelis = new SearchPelis(s);
        searchPelis.execute();
        List<Pelicula> peliculaList = searchPelis.get();
        return peliculaList;
    }
}