package com.example.kukoslokos.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.kukoslokos.MainRecyclerTab;
import com.example.kukoslokos.R;
import com.example.kukoslokos.datos.UsuarioDataSource;
import com.example.kukoslokos.model.Usuario;

public class RegistroFragment extends Fragment {

    public RegistroFragment() {
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
    public static RegistroFragment newInstance(String param1, String param2) {
        RegistroFragment fragment = new RegistroFragment();
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
        return inflater.inflate(R.layout.fragment_registro, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Button btnCancelarRegistro = getView().findViewById(R.id.btnCancelarRegistro);
        btnCancelarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginFragment loginFragment = new LoginFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, loginFragment).commit();
            }
        });

        String editTextNombre = ((EditText) getView().findViewById(R.id.editTextNombre)).getText().toString();
        String editTextApellidos = ((EditText) getView().findViewById(R.id.editTextApellidos)).getText().toString();
        String editTextNickname = ((EditText) getView().findViewById(R.id.editTextNickname)).getText().toString();
        String editTextEmail = ((EditText) getView().findViewById(R.id.editTextTextEmailAddress)).getText().toString();
        String editTextPassword = ((EditText) getView().findViewById(R.id.editTextContrasenaRegistro)).getText().toString();
        String editTextRepeatPassword = ((EditText) getView().findViewById(R.id.editTextContrasenaRegistro2)).getText().toString();

        Button btnFinalizarRegistro = getView().findViewById(R.id.btnFinalizarRegistro);
        btnFinalizarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFields(editTextNombre, editTextApellidos, editTextNickname, editTextEmail, editTextPassword, editTextRepeatPassword)){
                    UsuarioDataSource userds = new UsuarioDataSource(getContext());
                    //Usuario user = userds.registrar(editTextNombre, editTextApellidos, editTextNickname, editTextEmail, editTextPassword);

                    SharedPreferences.Editor editor = requireContext().getSharedPreferences(MainRecyclerTab.SHARED_PREFS, Context.MODE_PRIVATE).edit();
                    //editor.putInt(MainRecyclerTab.USER_ID_KEY, user.getId());
                    editor.apply();
                    ProfileFragment profileFragment = new ProfileFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, profileFragment).commit();
                }
            }
        });
    }

    private boolean checkFields(String nombre, String apellidos, String nickname, String email, String password, String repeatPassword){
        if (nombre.equals("") || apellidos.equals("") || nickname.equals("") || email.equals("") || password.equals("") || repeatPassword.equals("")){
            return false;
        }
        if (!password.equals(repeatPassword)){
            return false;
        }
       return true;
    }
}
