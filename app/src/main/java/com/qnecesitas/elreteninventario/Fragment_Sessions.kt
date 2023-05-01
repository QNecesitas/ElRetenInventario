package com.qnecesitas.elreteninventario

import android.content.DialogInterface
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
import com.qnecesitas.elreteninventario.databinding.LiAddSessionBinding
import com.qnecesitas.elreteninventario.network.RetrofitSessionImpIS
import com.shashank.sony.fancytoastlib.FancyToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Fragment_Sessions(var c_drawerS : String) : Fragment() {

    private var openSession: OpenSession? = null

    //Recycler
    private lateinit var binding: FragmentSessionsBinding
    private lateinit var al_sessions: ArrayList<ModelSession>
    private lateinit var adapterRSessions: AdapterRSessions

    //Internet
    private lateinit var retrofitSessionImpl: RetrofitSessionImpIS

    //Add Session
    private lateinit var li_binding: LiAddSessionBinding

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

        adapterRSessions.setEditListener(object: AdapterRSessions.RecyclerClickListener{
            override fun onClick(position: Int) {
                click_edit(position)
            }
        })
        adapterRSessions.setDeleteListener(object: AdapterRSessions.RecyclerClickListener{
            override fun onClick(position: Int) {
                click_delete(position)
            }
        })
        adapterRSessions.setRecyclerTouchListener(object: AdapterRSessions.RecyclerClickListener{
            override fun onClick(position: Int) {
                val c_sessionS = al_sessions.get(position).code
                openSession?.onSessionClicked(c_sessionS)
            }

        })
        binding.fsRecyclerSession.adapter = adapterRSessions
    }

    //Add sessions
    private fun click_add() {
        li_newDrawer()
    }

    private fun li_newDrawer() {
        val inflater = LayoutInflater.from(binding.root.context)
        li_binding = LiAddSessionBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_binding.root)
        val alertDialog = builder.create()
        var tietContent: String;

        li_binding.btnAccept.setOnClickListener {
            tietContent = li_binding.tiet.text.toString()
            if (tietContent.isNotEmpty()) addNewSessionInternet(tietContent)
            else li_binding.til.error = getString(R.string.este_campo_no_debe_vacio)
            alertDialog.dismiss()
        }
        li_binding.btnCancel.setOnClickListener { alertDialog.dismiss() }

        //Finalizado
        builder.setCancelable(true)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()


    }

    private fun addNewSessionInternet(sessionCode: String) {
        if (NetworkTools.isOnline(binding.root.context, true)) {
            val call = retrofitSessionImpl.addSession(Constants.PHP_TOKEN, sessionCode)
            call.enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        val model = ModelSession(sessionCode, c_drawerS)
                        al_sessions.add(model)
                        updateRecyclerAdapter()
                        FancyToast.makeText(
                            requireContext(),
                            getString(R.string.Operacion_realizada_con_exito),
                            FancyToast.LENGTH_LONG,
                            FancyToast.SUCCESS,
                            false
                        ).show()
                    } else {
                        FancyToast.makeText(
                            requireContext(),
                            getString(R.string.Revise_su_conexion),
                            FancyToast.LENGTH_LONG,
                            FancyToast.ERROR,
                            false
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<String>,
                    t: Throwable
                ) {
                    FancyToast.makeText(
                        requireContext(),
                        getString(R.string.Revise_su_conexion),
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,
                        false
                    ).show()
                }
            })
        }
    }

    //Edit session
    private fun click_edit(position: Int) {
        li_editSession(al_sessions[position].code, position)
    }

    private fun li_editSession(codeSessionOld: String, position: Int) {
        val inflater = LayoutInflater.from(binding.root.context)
        li_binding = LiAddSessionBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_binding.root)
        val alertDialog = builder.create()
        var tiedContent: String;

        li_binding.tiet.setText(codeSessionOld)
        li_binding.btnAccept.setOnClickListener {
            tiedContent = li_binding.tiet.text.toString()
            if (tiedContent.isNotEmpty()) editSessionInternet(codeSessionOld, tiedContent, position)
            else li_binding.til.error = getString(R.string.este_campo_no_debe_vacio)
            alertDialog.dismiss()
        }
        li_binding.btnCancel.setOnClickListener { alertDialog.dismiss() }

        //Finalizado
        builder.setCancelable(true)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun editSessionInternet(sessionCodeOld: String, sessionCodeNew: String, position: Int) {
        if (NetworkTools.isOnline(binding.root.context, true)) {
            val call =
                retrofitSessionImpl.updateSessions(Constants.PHP_TOKEN, sessionCodeOld, sessionCodeNew)
            call.enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        al_sessions[position].code = sessionCodeNew
                        updateRecyclerAdapter()
                        FancyToast.makeText(
                            requireContext(),
                            getString(R.string.Operacion_realizada_con_exito),
                            FancyToast.LENGTH_LONG,
                            FancyToast.SUCCESS,
                            false
                        ).show()
                    } else {
                        FancyToast.makeText(
                            requireContext(),
                            getString(R.string.Revise_su_conexion),
                            FancyToast.LENGTH_LONG,
                            FancyToast.ERROR,
                            false
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<String>,
                    t: Throwable
                ) {
                    FancyToast.makeText(
                        requireContext(),
                        getString(R.string.Revise_su_conexion),
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,
                        false
                    ).show()
                }
            })
        }
    }

    //Delete session
    private fun click_delete(position: Int) {
        showAlertDialogDeleteSession(position)
    }

    private fun showAlertDialogDeleteSession(position: Int) {
        //init alert dialog
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setCancelable(true)
        builder.setTitle(R.string.Eliminar_elemento)
        builder.setMessage(R.string.Desea_eliminar_la_seccion)
        //set listeners for dialog buttons
        builder.setPositiveButton(
            R.string.Si
        ) { dialog, _ ->
            if (NetworkTools.isOnline(requireContext(), true)) {
                dialog.dismiss()
                deleteSessionInternet(al_sessions[position].code, position)
            }
        }
        builder.setNegativeButton(R.string.No,
            DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() })

        //create the alert dialog and show it
        builder.create().show()
    }

    private fun deleteSessionInternet(sessionCode: String, position: Int) {
        if (NetworkTools.isOnline(binding.root.context, true)) {
            val call = retrofitSessionImpl.deleteSessions(Constants.PHP_TOKEN, sessionCode)
            call.enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        al_sessions.removeAt(position)
                        updateRecyclerAdapter()
                        FancyToast.makeText(
                            requireContext(),
                            getString(R.string.Operacion_realizada_con_exito),
                            FancyToast.LENGTH_LONG,
                            FancyToast.SUCCESS,
                            false
                        ).show()
                    } else {
                        FancyToast.makeText(
                            requireContext(),
                            getString(R.string.Revise_su_conexion),
                            FancyToast.LENGTH_LONG,
                            FancyToast.ERROR,
                            false
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<String>,
                    t: Throwable
                ) {
                    FancyToast.makeText(
                        requireContext(),
                        getString(R.string.Revise_su_conexion),
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,
                        false
                    ).show()
                }
            })
        }
    }



    fun setOpenSessionListener(openSession: OpenSession){
        this.openSession = openSession
    }

    interface OpenSession{
        fun onSessionClicked(c_sessions : String)
    }

}