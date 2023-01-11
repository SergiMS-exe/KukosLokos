package com.example.kukoslokos.util;

import retrofit2.Retrofit;

public class ApiUtil {

    public static KukosApi createKukosApi() {
            Retrofit retrofit= RetrofitClient.getClient(KukosApi.BASE_URL);

        return retrofit.create(KukosApi.class);
    }
}
