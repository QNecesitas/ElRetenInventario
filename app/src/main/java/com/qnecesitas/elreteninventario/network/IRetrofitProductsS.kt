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
        @Field("file") file: String,
        @Field("deficit") deficit: Int,
        @Field("size") size: String,
        @Field("brand") brand: String
    ) : Call<String>

    @FormUrlEncoded
    @POST("UpdateProduct.php")
    fun updateProduct(
        @Field("token") token: String,
        @Field("file") file: String,
        @Field("c_productS") c_productS : String,
        @Field("name") name : String,
        @Field("fk_c_sessionS") c_fk_sessionS: String,
        @Field("amount") amount: Int,
        @Field("buyPrice") buyPrice: Double,
        @Field("salePrice") salePrice: Double,
        @Field("descr") descr: String,
        @Field("deficit") deficit: Int,
        @Field("statePhoto") statePhoto: Int,
        @Field("size") size: String,
        @Field("brand") brand: String
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

    @GET("FetchProductsDeficit.php")
    fun fetchProductsDeficit(
        @Query("token") token: String,
        @Query("button") button: String
    ): Call<ArrayList<ModelEditProduct>>

    @FormUrlEncoded
    @POST("TransferProductS_LS.php")
    fun transferProducts(
        @Field("token") token : String,
        @Field("c_productS")c_productS: String,
        @Field("name") name: String,
        @Field("fk_c_sessionS") fk_c_sessionS: String,
        @Field("amount") amount: Int,
        @Field("buyPrice") buyPrice: Double,
        @Field("salePrice") salePrice: Double,
        @Field("descr") descr: String,
        @Field("statePhoto") statePhoto: Int,
        @Field("c_sessionLS") c_sessionLS: String,
        @Field("deficit") deficit: Int,
        @Field("exists") exists: Int,
        @Field("sendAll") sendAll: Int,
        @Field("size") size: String,
        @Field("brand") brand: String
    ): Call<String>

    @FormUrlEncoded
    @POST("AlterAmountS.php")
    fun alterAmountS(
        @Field("token")token: String,
        @Field("c_productS")c_productS: String,
        @Field("amount")amount: Int
    ): Call<String>

    @GET("FetchProductSPath.php")
    fun fetchProductSPath(
        @Query("token")token: String,
        @Query("c_productS")c_productS: String
    ): Call<ArrayList<ModelProductPath>>

}