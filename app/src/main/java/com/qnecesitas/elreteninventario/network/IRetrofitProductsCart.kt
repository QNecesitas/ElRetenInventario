package com.qnecesitas.elreteninventario.network

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IRetrofitProductsCart {

    @FormUrlEncoded
    @POST("AddOrder.php")
    fun addOrder(
        @Field("token") token: String,
        @Field("name") name: String,
        @Field("products") products: String,
        @Field("totalPrice") totalPrice: Double,
        @Field("totalInv") totalInv: Double,
        @Field("discount") discount: Double,
        @Field("type") type: String,
        @Field("totalTransf") totalTransf: Double
    ): Call<String>
}