package com.example.kukoslokos.util;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit= null;

    public static Retrofit getClient(String baseUrl) {

        if (retrofit==null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            retrofit= new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
}