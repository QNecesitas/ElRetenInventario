package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.data.ModelShelf
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.ArrayList

interface IRetrofitShelvesLS {


    @FormUrlEncoded
    @POST("UpdateShelfLS.php")
    fun updateShelf(
        @Field("token") token: String,
        @Field("c_shelfLSOld") c_shelfLSOld: String,
        @Field("c_shelfLSNew") c_ShelfLSNew: String,
        @Field("amount") amount: Int


    ) : Call<String>



    @FormUrlEncoded
    @POST("DeleteShelfLS.php")
    fun deleteShelf(
        @Field("token") token: String,
        @Field("c_shelfLS") c_shelfLS: String,
    ) : Call<String>



    @FormUrlEncoded
    @POST("AddShelfLS.php")
    fun addShelf(
        @Field("token") token: String,
        @Field("c_shelfLS") c_shelfLS: String,
    ) : Call<String>



    @GET("FetchShelvesLS.php")
    fun fetchShelves(
        @Query("token") token: String
    ): Call<ArrayList<ModelShelf>>
}