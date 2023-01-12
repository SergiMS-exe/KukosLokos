package com.example.kukoslokos.ui;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.kukoslokos.MainRecyclerTab;
import com.example.kukoslokos.R;
import com.example.kukoslokos.model.Usuario;
import com.example.kukoslokos.util.ApiUtil;
import com.example.kukoslokos.util.bodies.RegisterBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PARENT = "parent";



    private SharedPreferences sharedPreferences;
    private int userId;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String parent) {
        RegisterFragment fragment = new RegisterFragment();
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

        EditText editTextNombre = (EditText) getView().findViewById(R.id.editTextNombre);
        EditText editTextApellidos = (EditText) getView().findViewById(R.id.editTextApellidos);
        EditText editTextNickname = (EditText) getView().findViewById(R.id.editTextNickname);
        EditText editTextEmailAddress = (EditText) getView().findViewById(R.id.editTextEmailAddress);
        EditText editTextContrasenaRegistro = (EditText) getView().findViewById(R.id.editTextContrasenaRegistro);
        EditText editTextContrasenaRegistro2 = (EditText) getView().findViewById(R.id.editTextContrasenaRegistro2);

        Button btnFinalizarRegistro = getView().findViewById(R.id.btnFinalizarRegistro);
        btnFinalizarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFields(editTextNombre.getText().toString(), editTextApellidos.getText().toString(), editTextNickname.getText().toString(), editTextEmailAddress.getText().toString(), editTextContrasenaRegistro.getText().toString(), editTextContrasenaRegistro2.getText().toString())){
                    register(editTextNombre.getText().toString(), editTextEmailAddress.getText().toString(), editTextNickname.getText().toString(), editTextContrasenaRegistro.getText().toString());
                }
            }
        });
    }

    private void register(String nombre, String email, String nickName, String password){
        peticionRegister(nombre, email, nickName, password);
    }

    private void peticionRegister(String nombre, String email, String nickName, String password){
        Call<Usuario> call = ApiUtil.getKukosApi().register(new RegisterBody(nombre, email, nickName, password));

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                switch (response.code()){
                    case 200:
                        Usuario user = response.body(); //Cogemos el usuario

                        SharedPreferences.Editor editor = sharedPreferences.edit(); //Guardamos el usuario en sesion
                        editor.putString(MainRecyclerTab.USER_ID_KEY, user.getId());
                        editor.apply();

                        MainRecyclerTab.usuarioEnSesion=user;

                        goToHome();
                        break;
                    default:
                        call.cancel();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("Register - error", t.toString());
            }
        });
    }

    private void goToHome() {
        recreate();
    }

    private boolean checkFields(String nombre, String apellidos, String nickname, String email, String password, String repeatPassword){
        if (nombre.equals("") || apellidos.equals("") || nickname.equals("") || email.equals("") || password.equals("") || repeatPassword.equals("")){
            Toast.makeText(getContext(), "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equals(repeatPassword)){
            Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return false;
        }
       return true;
    }
}
