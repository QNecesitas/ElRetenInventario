package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.data.ModelPassword
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.ArrayList

interface IRetrofitPasswords {


    @FormUrlEncoded
    @POST("UpdateAccount.php")
    fun updateAccount(
        @Field("token") token: String,
        @Field("password") password: String,
        @Field("user") user: String
    ) : Call<String>


    @GET("FetchAccounts.php")
    fun fetchAccounts(
        @Query("token") token: String,
    ): Call<ArrayList<ModelPassword>>

}