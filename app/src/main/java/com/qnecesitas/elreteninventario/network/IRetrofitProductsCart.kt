package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.data.ModelEditProduct
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.ArrayList

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
        @Field("type") type: String
    ): Call<String>
}