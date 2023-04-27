package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.data.ModelDrawer
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.ArrayList

interface IRetrofitDrawersS {

    @FormUrlEncoded
    @POST("UpdateDrawerS.php")
    fun updateDrawer(
        @Field("token") token: String,
        @Field("codeShelf") codeDrawer : String,
    ) : Call<Boolean>



    @FormUrlEncoded
    @POST("DeleteDrawerS.php")
    fun deleteDrawer(
        @Field("token") token: String,
        @Field("codeShelf") codeDrawer : String,
    ) : Call<Boolean>



    @FormUrlEncoded
    @POST("AddDrawerS.php")
    fun addDrawer(
        @Field("token") token: String,
        @Field("codeDrawer") codeShelf: String,
    ) : Call<Boolean>



    @GET("FetchDrawersS.php")
    fun fetchDrawers(
        @Query("token") token: String
    ): Call<ArrayList<ModelDrawer>>


}