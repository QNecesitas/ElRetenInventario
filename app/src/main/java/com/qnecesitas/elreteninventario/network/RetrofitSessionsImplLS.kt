package com.qnecesitas.elreteninventario.network

import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelSessionS
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class RetrofitSessionsImplLS : IRetrofitSessionsLS {

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(Constants.PHP_FILES)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val productApi: IRetrofitSessionsLS = retrofit.create(IRetrofitSessionsLS::class.java)

    override fun updateSessions(
        token: String,
        c_sessionLSOld: String,
        c_sessionLSNew: String,
        fk_c_drawerLS: String,
        amount: Int
    ): Call<String> {
        return productApi.updateSessions(token,c_sessionLSOld, c_sessionLSNew, fk_c_drawerLS, amount)
    }

    override fun deleteSessions(
        token: String,
        c_sessionS: String,
        fk_c_drawerLS: String
    ): Call<String> {
        return productApi.deleteSessions(token, c_sessionS, fk_c_drawerLS)
    }

    override fun addSession(
        token: String,
        c_sessionLS: String,
        fk_c_drawerLS: String
    ): Call<String> {
        val newCode = "${fk_c_drawerLS}_${c_sessionLS}"
        return  productApi.addSession(token, newCode, fk_c_drawerLS)
    }

    override fun fetchSessions(
        token: String,
        fk_c_drawerLS: String
    ): Call<ArrayList<ModelSessionS>> {
        return productApi.fetchSessions(token, fk_c_drawerLS)
    }
}