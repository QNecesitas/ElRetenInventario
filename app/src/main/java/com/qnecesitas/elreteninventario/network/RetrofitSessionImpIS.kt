package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelSession
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class RetrofitSessionImpIS : IRetrofitSessions{

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(Constants.PHP_FILES)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val productApi: IRetrofitSessions = retrofit.create(IRetrofitSessions::class.java)

    override fun updateSessions(token: String, c_sessionSOld : String, c_sessionSNew : String): Call<String> {
        return productApi.updateSessions(token, c_sessionSOld,c_sessionSNew)
    }

    override fun deleteSessions(token: String, c_sessionS : String): Call<String> {
        return productApi.deleteSessions(token, c_sessionS)
    }

    override fun addSession(token: String, c_sessionS: String): Call<String> {
        return productApi.addSession(token, c_sessionS)
    }

    override fun fetchSessions(token: String): Call<ArrayList<ModelSession>> {
        return productApi.fetchSessions(token)
    }

}