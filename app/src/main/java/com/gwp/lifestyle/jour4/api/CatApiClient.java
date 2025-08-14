package com.gwp.lifestyle.jour4.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CatApiClient {
    @GET("/api/cats?limit=10&skip=0")
    Call<List<CatImageResponse>> getCatImages();

    @GET("/api/cats")
    Call<List<CatImageResponse>> getCatImages(
            @Query("limit") int limit,
            @Query("skip") int skip
    );
}
