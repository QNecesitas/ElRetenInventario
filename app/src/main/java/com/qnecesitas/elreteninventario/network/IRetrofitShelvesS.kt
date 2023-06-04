package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.data.ModelShelfS
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
        @Field("c_shelfSOld") c_shelfSOld: String,
        @Field("c_shelfSNew") c_ShelfSNew: String,
        @Field("amount") amount: Int
    ) : Call<String>



    @FormUrlEncoded
    @POST("DeleteShelfS.php")
    fun deleteShelf(
        @Field("token") token: String,
        @Field("c_shelfS") c_shelfS: String,
    ) : Call<String>



    @FormUrlEncoded
    @POST("AddShelfS.php")
    fun addShelf(
        @Field("token") token: String,
        @Field("c_shelfS") c_shelfS: String,
    ) : Call<String>



    @GET("FetchShelvesS.php")
    fun fetchShelves(
        @Query("token") token: String
    ): Call<ArrayList<ModelShelfS>>


}