package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.data.ModelSessionS
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.ArrayList

interface IRetrofitSessionsS {

    @FormUrlEncoded
    @POST("UpdateSessionS.php")
    fun updateSessions(
        @Field("token") token: String,
        @Field("c_sessionSOld") c_sessionSOld: String,
        @Field("c_sessionSNew") c_sessionSNew: String,
        @Field("fk_c_drawerS") fk_c_drawerS: String,
        @Field("amount") amount: Int
    ) : Call<String>



    @FormUrlEncoded
    @POST("DeleteSessionS.php")
    fun deleteSessions(
        @Field("token") token: String,
        @Field("c_sessionS") c_sessionS : String,
        @Field("fk_c_drawerS") fk_c_drawerS: String
    ) : Call<String>



    @FormUrlEncoded
    @POST("AddSessionS.php")
    fun addSession(
        @Field("token") token: String,
        @Field("c_sessionS") c_sessionS: String,
        @Field("fk_c_drawerS") fk_c_drawerS: String,
    ) : Call<String>



    @GET("FetchSessionsS.php")
    fun fetchSessions(
        @Query("token") token: String,
        @Query("fk_c_drawerS") fk_c_drawerS: String
    ): Call<ArrayList<ModelSessionS>>

}