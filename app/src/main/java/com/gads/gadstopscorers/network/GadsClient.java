package com.gads.gadstopscorers.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GadsClient {
    private static Retrofit retrofit = null;

    public static GadsApi getClient(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.GADS_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(GadsApi.class);
    }
}
