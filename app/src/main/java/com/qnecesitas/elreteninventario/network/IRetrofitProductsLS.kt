package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.data.ModelEditProduct
import com.qnecesitas.elreteninventario.data.ModelProductPath
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.ArrayList

interface IRetrofitProductsLS {

    @FormUrlEncoded
    @POST("AddProductLS.php")
    fun addProductLS(
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
    @POST("TransferProductSLS.php")
    fun transferProductsLS(
        @Field("token") token : String,
        @Field("c_productS")c_productS: String,
        @Field("name") name: String,
        @Field("fk_c_sessionS") fk_c_sessionS: String,
        @Field("amount") amount: Int,
        @Field("buyPrice") buyPrice: Double,
        @Field("salePrice") salePrice: Double,
        @Field("descr") descr: String,
        @Field("statePhoto") statePhoto: Int,
        @Field("c_sessionLS") c_sessionLS: String
    ): Call<String>


    @FormUrlEncoded
    @POST("AlterAmountSLS.php")
    fun alterAmountSLS(
        @Field("token")token: String,
        @Field("c_productS")c_productS: String,
        @Field("amount")amount: Int
    ): Call<String>


    @FormUrlEncoded
    @POST("UpdateProductLS.php")
    fun updateProductLS(
        @Field("token") token: String,
        @Field("c_productSOld") c_productSOld : String,
        @Field("file") file: String,
        @Field("c_productS") c_productS : String,
        @Field("name") name : String,
        @Field("fk_c_sessionS") c_fk_sessionS: String,
        @Field("amount") amount: Int,
        @Field("buyPrice") buyPrice: Double,
        @Field("salePrice") salePrice: Double,
        @Field("descr") descr: String,
    ) : Call<String>


    @FormUrlEncoded
    @POST("DeleteProductLS.php")
    fun deleteProductLS(
        @Field("token") token: String,
        @Field("c_productS") c_productS: String,
        @Field("fk_c_sessionS") fk_c_sessionS: String
    ): Call<String>


    @GET("FetchProductsLS.php")
    fun fetchProductsLS(
        @Query("token") token: String,
        @Query("fk_c_sessionLS") fk_c_sessionLS: String
    ): Call<ArrayList<ModelEditProduct>>


    @GET("FetchProductsLSAll.php")
    fun fetchProductsSAllLS(
        @Query("token") token: String
    ): Call<ArrayList<ModelEditProduct>>


    @GET("FetchProductSLSPath.php")
    fun fetchProductSPathLS(
        @Query("token")token: String,
        @Query("c_productS")c_productS: String
    ): Call<ArrayList<ModelProductPath>>

}