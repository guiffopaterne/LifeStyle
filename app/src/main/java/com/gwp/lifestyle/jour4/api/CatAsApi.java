package com.gwp.lifestyle.jour4.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CatAsApi {
    public static  String BASE_URL = "https://cataas.com";

    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static CatApiClient catAsApiClient = retrofit.create(CatApiClient.class);

}
