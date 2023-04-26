package com.research.retrofitrecyclerview;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("api")
    Call<UserListResponse> getHits(@Query("key") String Key, @Query("q") String Search, @Query("image_type") String ImageType, @Query("pretty") String PrettyState);
}
