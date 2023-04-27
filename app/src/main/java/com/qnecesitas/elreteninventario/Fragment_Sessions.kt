package com.qnecesitas.elreteninventario

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.qnecesitas.elreteninventario.adapters.AdapterRSessions
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.auxiliary.NetworkTools
import com.qnecesitas.elreteninventario.data.ModelSession
import com.qnecesitas.elreteninventario.databinding.FragmentSessionsBinding
import com.qnecesitas.elreteninventario.databinding.LiAddShelfBinding
import com.qnecesitas.elreteninventario.network.RetrofitSessionImpIS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Fragment_Sessions : Fragment() {


    //Recycler
    private lateinit var binding: FragmentSessionsBinding
    private lateinit var al_sessions: ArrayList<ModelSession>
    private lateinit var adapterRSessions: AdapterRSessions

    //Internet
    private lateinit var retrofitSessionImpl: RetrofitSessionImpIS

    //Add Shelf
    private lateinit var li_binding: LiAddShelfBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSessionsBinding.inflate(inflater)


        //Add Button
        binding.fsAddSession.setOnClickListener { click_add() }

        //Recycler
        al_sessions = ArrayList()
        adapterRSessions = AdapterRSessions(al_sessions, binding.root.context)
        binding.fsRecyclerSession.setHasFixedSize(true)
        binding.fsRecyclerSession.layoutManager = LinearLayoutManager(binding.root.context)
        binding.fsRecyclerSession.adapter = adapterRSessions

        //Retrofit
        retrofitSessionImpl = RetrofitSessionImpIS()

        //Internet
        binding.fsRetryConnectionSession.setOnClickListener { loadRecyclerInfo() }

        return binding.root
    }

    //Recycler information
    private fun loadRecyclerInfo() {
        if (NetworkTools.isOnline(binding.root.context, false)) {

            val call = retrofitSessionImpl.fetchSessions(Constants.PHP_TOKEN)
            call.enqueue(object : Callback<ArrayList<ModelSession>> {
                override fun onResponse(
                    call: Call<ArrayList<ModelSession>>,
                    response: Response<java.util.ArrayList<ModelSession>>
                ) {
                    if (response.isSuccessful) {
                        binding.fsNotConnectionSession.visibility = View.GONE
                        binding.fsRecyclerSession.visibility = View.VISIBLE
                        al_sessions = response.body()!!
                        updateRecyclerAdapter()
                    } else {
                        binding.fsNotConnectionSession.visibility = View.VISIBLE
                        binding.fsRecyclerSession.visibility = View.GONE
                    }
                }

                override fun onFailure(
                    call: Call<java.util.ArrayList<ModelSession>>,
                    t: Throwable
                ) {
                    binding.fsNotConnectionSession.visibility = View.VISIBLE
                    binding.fsRecyclerSession.visibility = View.GONE
                }
            })


        } else {
            binding.fsNotConnectionSession.visibility = View.VISIBLE
            binding.fsRecyclerSession.visibility = View.GONE
        }
    }

    private fun updateRecyclerAdapter() {
        if (al_sessions.isEmpty()) {
            binding.fsNotInfoSession.visibility = View.VISIBLE
            binding.fsRecyclerSession.visibility = View.GONE
        } else {
            binding.fsNotInfoSession.visibility = View.GONE
            binding.fsRecyclerSession.visibility = View.VISIBLE
        }
        adapterRSessions = AdapterRSessions(al_sessions, binding.root.context)
        binding.fsRecyclerSession.adapter = adapterRSessions
    }

    //Add shelf
    private fun click_add() {
        li_newDrawer()
    }

    private fun li_newDrawer() {
        val inflater = LayoutInflater.from(binding.root.context)
        li_binding = LiAddShelfBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_binding.root)
        val alertDialog = builder.create()


        //Finalizado
        builder.setCancelable(true)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()


    }

}