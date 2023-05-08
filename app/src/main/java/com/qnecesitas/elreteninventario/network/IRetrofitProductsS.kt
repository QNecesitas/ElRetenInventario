package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.data.ModelEditProduct
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.ArrayList

interface IRetrofitProductsS {

    @FormUrlEncoded
    @POST("AddProduct.php")
    fun addProduct(
        @Field("token") token: String,
        @Field("c_productS") c_productS : String,
        @Field("name") name : String,
        @Field("fk_c_sessionS") c_fk_sessionS: String,
        @Field("amount") amount: Int,
        @Field("buyPrice") buyPrice: Double,
        @Field("salePrice") salePrice: Double,
        @Field("descr") descr: String,
        @Field("file") file: String
    ) : Call<String>

    @FormUrlEncoded
    @POST("UpdateProduct.php")
    fun updateProduct(
        @Field("token") token: String,
        @Field("c_productS") c_productS : String,
        @Field("name") name : String,
        @Field("fk_c_sessionS") c_fk_sessionS: String,
        @Field("amount") amount: Int,
        @Field("buyPrice") buyPrice: Double,
        @Field("salePrice") salePrice: Double,
        @Field("descr") descr: String,
        @Field("file") file: String
    ) : Call<String>

    @GET("FetchProductsS.php")
    fun fetchProducts(
        @Query("token") token: String,
        @Query("fk_c_sessionS") fk_c_sessionS: String
    ): Call<ArrayList<ModelEditProduct>>

    @GET("FetchProductsSAll.php")
    fun fetchProductsSAll(
        @Query("token") token: String
    ): Call<ArrayList<ModelEditProduct>>

    @FormUrlEncoded
    @POST("DeleteProduct.php")
        fun deleteProduct(
            @Field("token") token: String,
            @Field("c_productS") c_productS: String,
            @Field("fk_c_sessionS") fk_c_sessionS: String
        ): Call<String>

    @GET("FetchProductsSDeficit.php")
    fun fetchProductsSDeficit(
        @Query("token") token: String,
        @Query("filter") filter: Int,
        @Query("button") button: String
    ): Call<ArrayList<ModelEditProduct>>

}