package com.qnecesitas.elreteninventario.network

import android.provider.SyncStateContract
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelPassword
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class RetrofitPasswords: IRetrofitPasswords{


    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(Constants.PHP_FILES)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val productApi: IRetrofitPasswords = retrofit.create(IRetrofitPasswords::class.java)

    override fun updateAccount(token: String, password: String, user: String): Call<Boolean> {
        return productApi.updateAccount(token, password,user)
    }


    override fun fetchAccounts(token: String): Call<ArrayList<ModelPassword>> {
        return productApi.fetchAccounts(token)
    }

}