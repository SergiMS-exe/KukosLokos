package com.example.kukoslokos.util;

import com.example.kukoslokos.model.Usuario;
import com.example.kukoslokos.util.bodies.LoginBody;
import com.example.kukoslokos.util.bodies.RegisterBody;
import com.example.kukoslokos.util.bodies.SaveMovieBody;
import com.example.kukoslokos.util.bodies.UpdateRuleBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface KukosApi {

    String BASE_URL = "https://kukos-server.vercel.app/";

    @POST("login")
    Call<Usuario> login(
            @Body LoginBody loginBody
    );

    @POST("register")
    Call<Usuario> register(
            @Body RegisterBody registerBody
    );

    @GET("getUserById")
    Call<Usuario> getUserById(
            @Query("_id") String id
    );

    @PUT("updateLastRule")
    Call<Usuario> updateLastRule(
            @Body UpdateRuleBody updateRuleBody
    );

    @POST("saveMovie")
    Call<Usuario> saveMovie(
            @Body SaveMovieBody saveMovieBody
    );
}
