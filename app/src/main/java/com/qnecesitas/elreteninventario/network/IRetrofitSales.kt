package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.data.ModelEditProduct
import com.qnecesitas.elreteninventario.data.ModelSale
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.Month
import java.util.ArrayList

interface IRetrofitSales {

    @GET("FetchOrdersAll.php")
    fun fetchOrdersAll(
        @Query("token") token: String
    ): Call<ArrayList<ModelSale>>

    @GET("FetchOrdersY.php")
    fun fetchOrdersY(
        @Query("token") token: String,
        @Query("year") year: Int
    ): Call<ArrayList<ModelSale>>

    @GET("FetchOrdersM.php")
    fun fetchOrdersM(
        @Query("token") token: String,
        @Query("year") year: Int,
        @Query("month") month: Int
    ): Call<ArrayList<ModelSale>>

    @GET("FetchOrdersD.php")
    fun fetchOrdersD(
        @Query("token") token: String,
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("day") day: Int
    ): Call<ArrayList<ModelSale>>

}