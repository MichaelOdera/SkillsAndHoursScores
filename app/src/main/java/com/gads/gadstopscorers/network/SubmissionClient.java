package com.gads.gadstopscorers.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubmissionClient {

    private static Retrofit retrofit = null;

    public static SubmissionApi getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.SUBMISSION_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(SubmissionApi.class);

    }
}
