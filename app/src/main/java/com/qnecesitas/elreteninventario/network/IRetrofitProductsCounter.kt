package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.data.ModelEditProduct
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.ArrayList

interface IRetrofitProductsCounter {

    @GET("FetchProductsCounter.php")
    fun fetchProducts(
        @Query("token") token: String,
    ): Call<ArrayList<ModelEditProduct>>


}