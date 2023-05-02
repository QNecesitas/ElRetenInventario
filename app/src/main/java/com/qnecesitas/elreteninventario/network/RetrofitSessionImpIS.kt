package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelSession
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class RetrofitSessionImpIS : IRetrofitSessionsS{

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(Constants.PHP_FILES)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val productApi: IRetrofitSessionsS = retrofit.create(IRetrofitSessionsS::class.java)

    override fun updateSessions(token: String, c_sessionSOld : String, c_sessionSNew : String, fk_c_drawerS: String, amount: Int): Call<String> {
        return productApi.updateSessions(token, c_sessionSOld,c_sessionSNew,fk_c_drawerS, amount)
    }

    override fun deleteSessions(token: String, c_sessionS : String, fk_c_drawerS: String): Call<String> {
        return productApi.deleteSessions(token, c_sessionS,fk_c_drawerS)
    }

    override fun addSession(token: String, c_sessionS: String, fk_c_drawerS: String): Call<String> {
        return productApi.addSession(token, c_sessionS, fk_c_drawerS)
    }

    override fun fetchSessions(token: String, fk_c_drawerS: String): Call<ArrayList<ModelSession>> {
        return productApi.fetchSessions(token, fk_c_drawerS)
    }

}