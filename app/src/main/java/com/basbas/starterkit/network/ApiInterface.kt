package com.basbas.starterkit.network

import com.basbas.starterkit.entity.ResponseMeal
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("filter.php")
    fun loadDataMeal(
        @Query("a") area: String
    ) : Call<ResponseMeal>
}