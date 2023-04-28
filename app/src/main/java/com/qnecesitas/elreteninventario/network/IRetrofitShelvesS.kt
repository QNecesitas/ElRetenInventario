package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.data.ModelShelf
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.ArrayList

interface IRetrofitShelvesS {


    @FormUrlEncoded
    @POST("UpdateShelfS.php")
    fun updateShelf(
        @Field("token") token: String,
        @Field("c_ShelfSOld") c_ShelfSOld: String,
        @Field("c_ShelfSNew") c_ShelfSNew: String,
    ) : Call<String>



    @FormUrlEncoded
    @POST("DeleteShelfS.php")
    fun deleteShelf(
        @Field("token") token: String,
        @Field("c_ShelfS") c_ShelfS: String,
    ) : Call<String>



    @FormUrlEncoded
    @POST("AddShelfS.php")
    fun addShelf(
        @Field("token") token: String,
        @Field("c_ShelfS") c_ShelfS: String,
    ) : Call<String>



    @GET("FetchShelvesS.php")
    fun fetchShelves(
        @Query("token") token: String
    ): Call<ArrayList<ModelShelf>>


}