package com.example.kukoslokos.util;

import com.example.kukoslokos.model.Usuario;
import com.example.kukoslokos.util.bodies.LoginBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KukosApi {

    String BASE_URL = "https://kukos-server.vercel.app/";

    @POST("login")
    Call<Usuario> login(
        @Body LoginBody loginBody
    );
}