package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.data.ModelShelf
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.ArrayList

interface IRetrofitShelves {


    @FormUrlEncoded
    @POST("UpdateShelf.php")
    fun updateShelf(
        @Field("token") token: String,
        @Field("codeShelf") codeShelf: String,
    ) : Call<Boolean>



    @FormUrlEncoded
    @POST("DeleteShelf.php")
    fun deleteShelf(
        @Field("token") token: String,
        @Field("codeShelf") codeShelf: String,
    ) : Call<Boolean>



    @FormUrlEncoded
    @POST("AddShelf.php")
    fun addShelf(
        @Field("token") token: String,
        @Field("codeShelf") codeShelf: String,
    ) : Call<Boolean>



    @GET("FetchShelves.php")
    fun fetchShelves(
        @Query("token") token: String,
        @Query("isCounter") isCounter: Boolean
    ): Call<ArrayList<ModelShelf>>


}