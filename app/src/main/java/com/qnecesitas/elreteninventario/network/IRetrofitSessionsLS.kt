package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.data.ModelSessionS
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.ArrayList

interface IRetrofitSessionsLS {

    @FormUrlEncoded
    @POST("UpdateSessionLS.php")
    fun updateSessions(
        @Field("token") token: String,
        @Field("c_sessionLSOld") c_sessionLSOld: String,
        @Field("c_sessionLSNew") c_sessionLSNew: String,
        @Field("fk_c_drawerLS") fk_c_drawerLS: String,
        @Field("amount") amount: Int
    ) : Call<String>



    @FormUrlEncoded
    @POST("DeleteSessionLS.php")
    fun deleteSessions(
        @Field("token") token: String,
        @Field("c_sessionLS") c_sessionS : String,
        @Field("fk_c_drawerLS") fk_c_drawerLS: String
    ) : Call<String>



    @FormUrlEncoded
    @POST("AddSessionLS.php")
    fun addSession(
        @Field("token") token: String,
        @Field("c_sessionLS") c_sessionLS: String,
        @Field("fk_c_drawerLS") fk_c_drawerLS: String,
    ) : Call<String>



    @GET("FetchSessionsLS.php")
    fun fetchSessions(
        @Query("token") token: String,
        @Query("fk_c_drawerLS") fk_c_drawerLS: String
    ): Call<ArrayList<ModelSessionS>>

}