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
        @Field("c_drawerSOld") c_drawerSOld : String,
        @Field("c_drawerSNew") c_drawerSNew : String,
        @Field("fk_c_shelfS") fk_c_shelfS: String,
        @Field("amount") amount: Int
    ) : Call<String>



    @FormUrlEncoded
    @POST("DeleteDrawerS.php")
    fun deleteDrawer(
        @Field("token") token: String,
        @Field("c_drawerS") c_drawerS : String,
        @Field("fk_c_shelfS") fk_c_shelfS: String
    ) : Call<String>



    @FormUrlEncoded
    @POST("AddDrawerS.php")
    fun addDrawer(
        @Field("token") token: String,
        @Field("c_drawerS") c_drawerS: String,
        @Field("fk_c_shelfS") fk_c_shelfS: String
    ) : Call<String>



    @GET("FetchDrawersS.php")
    fun fetchDrawers(
        @Query("token") token: String,
        @Query("fk_c_shelfS") fk_c_shelfS: String
    ): Call<ArrayList<ModelDrawer>>


}