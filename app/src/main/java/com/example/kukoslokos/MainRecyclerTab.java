package com.example.kukoslokos;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.kukoslokos.model.Pelicula;
import com.example.kukoslokos.tasks.SearchPelis;
import com.example.kukoslokos.ui.HomeFragment;
import com.example.kukoslokos.ui.LoginFragment;
import com.example.kukoslokos.ui.ProfileFragment;
import com.example.kukoslokos.ui.SavedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainRecyclerTab extends AppCompatActivity implements Animation.AnimationListener {

    //Atributos de la ruleta
    boolean blnButtonRotation = true;
    boolean finRuleta = false;
    int intNumber = 11;
    long lngDregrees = 0;
    SharedPreferences sharedPreferences;
    SearchView searchView;
    ImageView f_Ruleta, b_Ruleta;
    Button buttonGirar;


    // KEYS
    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";
    // key for storing user id.
    public static final String USER_ID_KEY = "user_id_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cargadoRuleta();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navBarListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            getSupportActionBar().hide();
            switch (item.getItemId()) {
                case R.id.home:
                    //Creamos el framento de información
                    while(!finRuleta){}
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
        searchView = (SearchView) menuItem.getActionView();
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

    private void cargadoRuleta() {

        if (finRuleta){
            homeMenu();
        } else {
            getWindow().addFlags(1024);
            requestWindowFeature(1);

            setContentView(R.layout.fragment_ruleta);

            buttonGirar = findViewById(R.id.buttonGirar);
            b_Ruleta = (ImageView) findViewById(R.id.BaseRuleta);
            f_Ruleta = (ImageView) findViewById(R.id.FlechaRuleta);

            this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            this.intNumber = this.sharedPreferences.getInt("INT_NUMBER", 11);
            b_Ruleta.setImageDrawable(getResources().getDrawable(R.drawable.ic_base_ruleta));
            getSupportActionBar().hide();
        }
    }

    private void homeMenu() {
        setContentView(R.layout.activity_recycler_tab);

        //Gestion de la barra de navegacion
        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        navView.setOnNavigationItemSelectedListener(navBarListener);

        navView.setSelectedItemId(R.id.home);
    }

    public void onClinkButtonRotation(View view){

        if(this.blnButtonRotation){

            int run = new Random().nextInt(360) + 3600;
            RotateAnimation rotateAnimation = new RotateAnimation((float)this.lngDregrees,
                    (float) (this.lngDregrees + ((long)run)),
                    1, 0.5f, 1, 0.5f);
            this.lngDregrees = (this.lngDregrees + ((long)run)) % 360;
            rotateAnimation.setDuration((long)run);
            rotateAnimation.setFillAfter(true);
            rotateAnimation.setInterpolator(new DecelerateInterpolator());
            rotateAnimation.setAnimationListener(this);
            b_Ruleta.startAnimation(rotateAnimation);

            Animation animationGuardar = AnimationUtils.loadAnimation(MainRecyclerTab.this, R.anim.blink_anim);
            buttonGirar.setAnimation(animationGuardar);

        }
    }

    @Override
    public void onAnimationStart(Animation animation) {
        this.blnButtonRotation = false;
        buttonGirar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        int[] puntos = new int[]{25, 200, 50, 150, 25, 75, 50, 75, 25, 50, 100};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Felicidades ");
        builder.setMessage("Has ganado " + puntos[((int)(((double)this.intNumber)
                - Math.floor(((double)this.lngDregrees)/(360.0d/((double)this.intNumber)))))-1] + " puntos");
        builder.setPositiveButton("Aceptar", null);

        AlertDialog dialog = builder.create();
        dialog.show();

       // Toast toast = Toast.makeText(this, "Felicidades " + " has ganado " + puntos[((int)(((double)this.intNumber)
               // - Math.floor(((double)this.lngDregrees)/(360.0d/((double)this.intNumber)))))-1] + " puntos", Toast.LENGTH_SHORT);
        //toast.setGravity(49, 0, 0);
        //toast.show();
        this.blnButtonRotation = true;
        buttonGirar.setVisibility(View.VISIBLE);
        try {
            Thread.sleep(1000);
            finRuleta = true;
            homeMenu();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}