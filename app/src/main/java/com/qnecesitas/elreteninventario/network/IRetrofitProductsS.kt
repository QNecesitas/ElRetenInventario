package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.data.ModelEditProduct
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.ArrayList

interface IRetrofitProductsS {



    @GET("FetchProductsS.php")
    fun fetchProducts(
        @Query("token") token: String,
        @Query("fk_c_sessionS") fk_c_sessionS: String
    ): Call<ArrayList<ModelEditProduct>>

    @GET("FetchProductsSAll.php")
    fun fetchProductsSAll(
        @Query("token") token: String
    ): Call<ArrayList<ModelEditProduct>>

}