package com.kishan.heady_test_app.repository;

import com.kishan.heady_test_app.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryProductListService {

    String BASE_URL = "https://stark-spire-93433.herokuapp.com/";

    @GET("json")
    Call<ApiResponse> getCategoryProductList();
}
