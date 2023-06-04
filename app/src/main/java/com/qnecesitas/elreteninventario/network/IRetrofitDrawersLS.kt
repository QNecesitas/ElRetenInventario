package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.data.ModelDrawerS
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.ArrayList

interface IRetrofitDrawersLS {

    @FormUrlEncoded
    @POST("UpdateDrawerLS.php")
    fun updateDrawer(
        @Field("token") token: String,
        @Field("c_drawerLSOld") c_drawerSOld : String,
        @Field("c_drawerLSNew") c_drawerSNew : String,
        @Field("fk_c_shelfLS") fk_c_shelfS: String,
        @Field("amount") amount: Int
    ) : Call<String>



    @FormUrlEncoded
    @POST("DeleteDrawerLS.php")
    fun deleteDrawer(
        @Field("token") token: String,
        @Field("c_drawerLS") c_drawerS : String,
        @Field("fk_c_shelfLS") fk_c_shelfS: String
    ) : Call<String>



    @FormUrlEncoded
    @POST("AddDrawerLS.php")
    fun addDrawer(
        @Field("token") token: String,
        @Field("c_drawerLS") c_drawerS: String,
        @Field("fk_c_shelfLS") fk_c_shelfS: String
    ) : Call<String>



    @GET("FetchDrawersLS.php")
    fun fetchDrawers(
        @Query("token") token: String,
        @Query("fk_c_shelfLS") fk_c_shelfS: String
    ): Call<ArrayList<ModelDrawerS>>


}