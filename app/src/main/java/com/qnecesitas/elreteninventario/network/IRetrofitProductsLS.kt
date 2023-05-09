package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.data.ModelEditProduct
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.ArrayList

interface IRetrofitProductsLS {

    @GET("FetchProductsLS.php")
    fun fetchProducts(
        @Query("token") token: String,
        @Query("fk_c_sessionLS") fk_c_sessionLS: String
    ): Call<ArrayList<ModelEditProduct>>

    @GET("FetchProductsLSAll.php")
    fun fetchProductsSAll(
        @Query("token") token: String
    ): Call<ArrayList<ModelEditProduct>>



}