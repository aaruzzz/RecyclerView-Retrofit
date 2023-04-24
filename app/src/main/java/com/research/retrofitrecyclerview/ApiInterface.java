package com.research.retrofitrecyclerview;

import retrofit2.http.GET;
import retrofit2.Call;

public interface ApiInterface {
    @GET("api/?key=5303976-fd6581ad4ac165d1b75cc15b3&q=meme&image_type=photo&pretty=true")
    Call<UserListResponse> getHits();
}
