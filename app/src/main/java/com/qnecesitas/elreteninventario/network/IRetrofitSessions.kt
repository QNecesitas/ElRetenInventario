package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.data.ModelDrawer
import com.qnecesitas.elreteninventario.data.ModelSession
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.ArrayList

interface IRetrofitSessions {

    @FormUrlEncoded
    @POST("UpdateSessions.php")
    fun updateSessions(
        @Field("token") token: String,
        @Field("c_sessionSOld") c_sessionSOld: String,
        @Field("c_sessionSNew") c_sessionSNew: String,
    ) : Call<String>



    @FormUrlEncoded
    @POST("DeleteSessions.php")
    fun deleteSessions(
        @Field("token") token: String,
        @Field("c_sessionS") c_sessionS : String,
    ) : Call<String>



    @FormUrlEncoded
    @POST("AddSession.php")
    fun addSession(
        @Field("token") token: String,
        @Field("c_sessionS") c_sessionS: String,
    ) : Call<String>



    @GET("FetchSessions.php")
    fun fetchSessions(
        @Query("token") token: String
    ): Call<ArrayList<ModelSession>>

}