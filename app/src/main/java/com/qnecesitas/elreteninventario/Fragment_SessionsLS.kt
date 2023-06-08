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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.qnecesitas.elreteninventario.adapters.AdapterRSessions
import com.qnecesitas.elreteninventario.adapters.AdapterRSessionsLS
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelSessionLS
import com.qnecesitas.elreteninventario.data.ModelSessionS
import com.qnecesitas.elreteninventario.database.Repository
import com.qnecesitas.elreteninventario.databinding.FragmentSessionsLsBinding
import com.qnecesitas.elreteninventario.databinding.LiAddSessionBinding
import com.qnecesitas.elreteninventario.network.RetrofitSessionsImplLS
import com.shashank.sony.fancytoastlib.FancyToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Fragment_SessionsLS(var c_drawerLS: String) : Fragment() {
    private var openSession: OpenSessionLS? = null

    //Recycler
    private lateinit var binding: FragmentSessionsLsBinding
    private lateinit var al_sessionLS: ArrayList<ModelSessionLS>
    private lateinit var adapterRSessions: AdapterRSessionsLS

    //Internet
    private lateinit var repository: Repository

    //Add Session
    private lateinit var li_binding: LiAddSessionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSessionsLsBinding.inflate(inflater)


        //Add Button
        binding.fsAddSession.setOnClickListener { click_add() }

        //Refresh
        binding.refresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                loadRecyclerInfo()
            }
        })

        //Recycler
        al_sessionLS = ArrayList()
        adapterRSessions = AdapterRSessionsLS(al_sessionLS, binding.root.context)
        binding.fsRecyclerSession.setHasFixedSize(true)
        binding.fsRecyclerSession.layoutManager = LinearLayoutManager(binding.root.context)
        binding.fsRecyclerSession.adapter = adapterRSessions

        //Retrofit
        repository = Repository(requireActivity().application)
        loadRecyclerInfo()

        loadRecyclerInfo()
        return binding.root
    }


    //Recycler information
    private fun loadRecyclerInfo() {
             al_sessionLS = repository.fetchSessionsLS(c_drawerLS)
                        binding.fsRecyclerSession.visibility = View.VISIBLE
                        binding.fsNotInfoSession.visibility = View.GONE
                        updateRecyclerAdapter()
    }

    private fun updateRecyclerAdapter() {
        if(al_sessionLS.isNotEmpty()){
            al_sessionLS.sortBy { it.c_sessionLS }
        }

        if (al_sessionLS.isEmpty()) {
            binding.fsNotInfoSession.visibility = View.VISIBLE
            binding.fsRecyclerSession.visibility = View.GONE
        } else {
            binding.fsNotInfoSession.visibility = View.GONE
            binding.fsRecyclerSession.visibility = View.VISIBLE
        }
        adapterRSessions = AdapterRSessionsLS(al_sessionLS, binding.root.context)

        adapterRSessions.setEditListener(object : AdapterRSessionsLS.RecyclerClickListener {
            override fun onClick(position: Int) {
                click_edit(position)
            }
        })
        adapterRSessions.setDeleteListener(object : AdapterRSessionsLS.RecyclerClickListener {
            override fun onClick(position: Int) {
                click_delete(position)
            }
        })
        adapterRSessions.setRecyclerTouchListener(object : AdapterRSessionsLS.RecyclerClickListener {
            override fun onClick(position: Int) {
                val c_sessionS = al_sessionLS.get(position).c_sessionLS
                openSession?.onSessionLSClicked(c_sessionS)
            }

        })
        binding.fsRecyclerSession.adapter = adapterRSessions
    }

    //Add sessions
    private fun click_add() {
        li_newSession()
    }

    private fun li_newSession() {
        val inflater = LayoutInflater.from(binding.root.context)
        li_binding = LiAddSessionBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_binding.root)
        val alertDialog = builder.create()
        var tietContent: String;

        li_binding.btnAccept.setOnClickListener {
            tietContent = li_binding.tiet.text.toString()
            if (tietContent.trim().isNotEmpty()) {
                if (!isDuplicated(tietContent)) {
                    if (!tietContent.contains("_")) {
                        addNewSessionInternet(tietContent)
                        alertDialog.dismiss()
                    }else li_binding.til.error = getString(R.string.No_permitido_simbol)
                }else li_binding.til.error = getString(R.string.Ya_existe_seccion)
            } else li_binding.til.error = getString(R.string.este_campo_no_debe_vacio)
        }
        li_binding.btnCancel.setOnClickListener { alertDialog.dismiss() }

        //Finalizado
        builder.setCancelable(true)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun addNewSessionInternet(sessionCode: String) {
        repository.addSessionLS(sessionCode, c_drawerLS)
                        val model = ModelSessionLS(sessionCode, c_drawerLS)
                        al_sessionLS.add(model)
                        updateRecyclerAdapter()
                        FancyToast.makeText(
                            requireContext(),
                            getString(R.string.Operacion_realizada_con_exito),
                            FancyToast.LENGTH_LONG,
                            FancyToast.SUCCESS,
                            false
                        ).show()
    }

    private fun isDuplicated(name: String): Boolean{
        for(shelf in al_sessionLS){
            val positionSymbol = shelf.c_sessionLS.lastIndexOf("_")
            val codeSessionPrepared = shelf.c_sessionLS.substring(positionSymbol+1)
            if(codeSessionPrepared == name){
                return true
            }
        }
        return false
    }

    //Edit session
    private fun click_edit(position: Int) {
        li_editSession(al_sessionLS[position].c_sessionLS, position)
    }

    private fun li_editSession(codeSessionOld: String, position: Int) {
        val inflater = LayoutInflater.from(binding.root.context)
        li_binding = LiAddSessionBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_binding.root)
        val alertDialog = builder.create()
        var tiedContent: String;

        val guionPosition = codeSessionOld.lastIndexOf("_")
        val newCode = codeSessionOld.substring(guionPosition + 1)
        li_binding.tiet.setText(newCode)
        li_binding.btnAccept.setOnClickListener {
            tiedContent = li_binding.tiet.text.toString()
            if (tiedContent.trim().isNotEmpty()) {
                editSessionInternet(codeSessionOld, tiedContent, position)
                alertDialog.dismiss()
            } else li_binding.til.error = getString(R.string.este_campo_no_debe_vacio)
        }
        li_binding.btnCancel.setOnClickListener { alertDialog.dismiss() }

        //Finalizado
        builder.setCancelable(true)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun editSessionInternet(sessionCodeOld: String, sessionCodeNew: String, position: Int) {
        repository.updateSessionLS(
            sessionCodeOld,
            sessionCodeNew,
            al_sessionLS[position].fk_c_drawerLS,
            al_sessionLS[position].amount
        )
        al_sessionLS[position].c_sessionLS = sessionCodeNew
        updateRecyclerAdapter()
        FancyToast.makeText(
            requireContext(),
            getString(R.string.Operacion_realizada_con_exito),
            FancyToast.LENGTH_LONG,
            FancyToast.SUCCESS,
            false
        ).show()
    }

    //Delete session
    private fun click_delete(position: Int) {
        val amount = al_sessionLS[position].amount
        if(amount == 0) {
            showAlertDialogDeleteSession(position)
        }else{
            showAlertDialogNotEmpty(amount)
        }
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

                dialog.dismiss()
                deleteSessionInternet(al_sessionLS[position].c_sessionLS, position)

        }
        builder.setNegativeButton(R.string.No,
            DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() })

        //create the alert dialog and show it
        builder.create().show()
    }

    private fun deleteSessionInternet(sessionCode: String, position: Int) {
       repository.deleteSessionLS(
                sessionCode,
                al_sessionLS[position].fk_c_drawerLS
            )
                        al_sessionLS.removeAt(position)
                        updateRecyclerAdapter()
                        FancyToast.makeText(
                            requireContext(),
                            getString(R.string.Operacion_realizada_con_exito),
                            FancyToast.LENGTH_LONG,
                            FancyToast.SUCCESS,
                            false
                        ).show()
    }

    private fun showAlertDialogNotEmpty(amount: Int) {
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setCancelable(true)
            .setTitle(getString(R.string.elemento_no_vaciado))
            .setMessage(getString(R.string.debe_eliminar_todo,amount))
            .setPositiveButton(R.string.Aceptar) { dialog, _ ->
                dialog.dismiss()
            }

        //create the alert dialog and show it
        builder.create().show()
    }

    fun setOpenSessionLSListener(openSessionLS: OpenSessionLS) {
        this.openSession = openSessionLS
    }

    interface OpenSessionLS {
        fun onSessionLSClicked(c_sessionsLS: String)
    }
}