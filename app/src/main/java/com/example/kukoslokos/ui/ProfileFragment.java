package com.example.kukoslokos.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.kukoslokos.MainRecyclerTab;
import com.example.kukoslokos.R;
import com.example.kukoslokos.datos.UsuarioDataSource;
import com.example.kukoslokos.model.Usuario;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    boolean modoNoche;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(MainRecyclerTab.SHARED_PREFS, Context.MODE_PRIVATE);
        if (sharedPreferences.getInt(MainRecyclerTab.USER_ID_KEY, -1)==-1){
            LoginFragment loginFragment = LoginFragment.newInstance(LoginFragment.PROFILE);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, loginFragment).commit();
        }else{
            UsuarioDataSource userds = new UsuarioDataSource(getContext());
            Usuario user = userds.getUserById(sharedPreferences.getInt(MainRecyclerTab.USER_ID_KEY, -1));

            TextView txtCorreo = (TextView) getView().findViewById(R.id.textEmail);
            txtCorreo.setText(user.getEmail());
        }

        ImageView imgBoton = (ImageView) getView().findViewById(R.id.imageTema);
        ImageView imgCambio = (ImageView) getView().findViewById(R.id.imageCambio);
        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Tema claro,
                imgBoton.setImageResource(R.drawable.ic_sun);
                imgCambio.setImageResource(R.drawable.ic_cambio_sun);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Tema oscuro
                imgBoton.setImageResource(R.drawable.ic_moon);
                imgCambio.setImageResource(R.drawable.ic_cambio_moon);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Button btnLogout = (Button) getView().findViewById(R.id.btn_cerrar_sesion);
        ImageView imgPerfil = (ImageView) getView().findViewById(R.id.imagenPerfil);
        ImageView imgCambio = (ImageView) getView().findViewById(R.id.imageCambio);
        ImageView imgBoton = (ImageView) getView().findViewById(R.id.imageTema);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animationGuardar = AnimationUtils.loadAnimation(getContext(), R.anim.fadeout);
                btnLogout.setAnimation(animationGuardar);

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainRecyclerTab.SHARED_PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.clear().apply();
                onStart();
            }
        });

        imgBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtengo el tema actual
                int currentNightMode = getResources().getConfiguration().uiMode
                        & Configuration.UI_MODE_NIGHT_MASK;
                switch (currentNightMode) {
                    case Configuration.UI_MODE_NIGHT_NO:
                        // Tema claro, cambio a oscuro
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        modoNoche = true;
                        imgBoton.setImageResource(R.drawable.ic_moon);
                        imgCambio.setImageResource(R.drawable.ic_cambio_moon);
                        break;
                    case Configuration.UI_MODE_NIGHT_YES:
                        // Tema oscuro, cambio a claro
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        modoNoche = false;
                        imgBoton.setImageResource(R.drawable.ic_sun);
                        imgCambio.setImageResource(R.drawable.ic_cambio_sun);
                        break;
                }
            }
        });


        imgCambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] avatarIds = {R.drawable.ic_avatar_0, R.drawable.ic_avatar_1,
                        R.drawable.ic_avatar_2, R.drawable.ic_avatar_3, R.drawable.ic_avatar_4,
                        R.drawable.ic_avatar_5, R.drawable.ic_avatar_6, R.drawable.ic_avatar_7,
                        R.drawable.ic_avatar_8, R.drawable.ic_avatar_9, R.drawable.ic_avatar_10,
                        R.drawable.ic_avatar_11, R.drawable.ic_avatar_12, R.drawable.ic_avatar_13,
                        R.drawable.ic_avatar_4, R.drawable.ic_avatar_15, R.drawable.ic_avatar_16,
                        R.drawable.ic_avatar_17, R.drawable.ic_avatar_18, R.drawable.ic_avatar_19 };

                Random random = new Random();
                int randomIndex = random.nextInt(avatarIds.length);

                imgPerfil.setImageResource(avatarIds[randomIndex]);
            }
        });
    }

}