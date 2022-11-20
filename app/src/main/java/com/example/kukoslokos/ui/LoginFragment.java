package com.example.kukoslokos.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kukoslokos.MainRecyclerTab;
import com.example.kukoslokos.R;
import com.example.kukoslokos.datos.UsuarioDataSource;
import com.example.kukoslokos.model.Usuario;

public class LoginFragment extends Fragment {

    public static final String SAVED = "saved";
    public static final String PROFILE = "profile";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PARENT = "parent";

    // TODO: Rename and change types of parameters
    private String parent;

    private SharedPreferences sharedPreferences;
    private int userId;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String parent) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(PARENT, parent);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            parent = getArguments().getString(PARENT);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        //Obtenemos los campos de texto del email y la password
        EditText emailEditText = (EditText) getView().findViewById(R.id.emailAddress);
        EditText passwordEditText = (EditText) getView().findViewById(R.id.password);
        Button loginBtn = (Button) getView().findViewById(R.id.btn_iniciar_sesion);

        Button btnRegistro = (Button)getView().findViewById(R.id.btnRegistrarse);
        btnRegistro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                RegistroFragment registroFragment = new RegistroFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.mainFragment, registroFragment).commit();
            }
        });

        //Obtenemos los datos del sharedPreferences
        sharedPreferences = requireContext().getSharedPreferences(MainRecyclerTab.SHARED_PREFS, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("USER_ID_KEY",-1);

        //listener para el boton
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(emailEditText.getText().toString(), passwordEditText.getText().toString());

            }
        });
    }

    private void login(String email, String password){
        /*if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(getContext(), "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
        }
        else {*/
            UsuarioDataSource userds = new UsuarioDataSource(getContext());
            Usuario user = userds.login(email, password);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(MainRecyclerTab.USER_ID_KEY, user.getId());
            editor.apply();

            goToParent();
        //}
    }

    private void goToParent() {
        switch (parent) {
            case SAVED:
                SavedFragment savedFragment = new SavedFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, savedFragment).commit();
                break;

            default:
                ProfileFragment profileFragment = new ProfileFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, profileFragment).commit();

        }
    }

}