package com.example.kukoslokos.util;

import retrofit2.Retrofit;

public class ApiUtil {

    private static KukosApi kukosApi;

    public static KukosApi getKukosApi() {
        if (kukosApi==null)
            kukosApi=RetrofitClient.getClient(KukosApi.BASE_URL).create(KukosApi.class);

        return kukosApi;
    }
}
