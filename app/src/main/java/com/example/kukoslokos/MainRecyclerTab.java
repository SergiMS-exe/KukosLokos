package com.example.kukoslokos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.kukoslokos.ui.HomeFragment;
import com.example.kukoslokos.ui.LoginFragment;
import com.example.kukoslokos.ui.ProfileFragment;
import com.example.kukoslokos.ui.SavedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

            switch (item.getItemId()) {
                case R.id.home:
                    //Creamos el framento de información
                    HomeFragment homeFragment = new HomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, homeFragment).commit();

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



}