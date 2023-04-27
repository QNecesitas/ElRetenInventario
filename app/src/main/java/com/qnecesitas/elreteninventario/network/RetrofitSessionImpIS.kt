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

    override fun updateSessions(token: String, codeSession : String): Call<Boolean> {
        return productApi.updateSessions(token, codeSession)
    }

    override fun deleteSessions(token: String, codeDrawer : String): Call<Boolean> {
        return productApi.deleteSessions(token, codeDrawer)
    }

    override fun addSession(token: String, codeShelf: String): Call<Boolean> {
        return productApi.addSession(token, codeShelf)
    }

    override fun fetchSessions(token: String): Call<ArrayList<ModelSession>> {
        return productApi.fetchSessions(token)
    }

}