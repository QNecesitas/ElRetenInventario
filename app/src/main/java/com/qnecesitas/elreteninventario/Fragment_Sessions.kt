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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.qnecesitas.elreteninventario.adapters.AdapterRSessions
import com.qnecesitas.elreteninventario.data.ModelSessionS
import com.qnecesitas.elreteninventario.database.Repository
import com.qnecesitas.elreteninventario.databinding.FragmentSessionsBinding
import com.qnecesitas.elreteninventario.databinding.LiAddSessionBinding
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.launch

class Fragment_Sessions(var c_drawerS: String, var c_shelfS: String) : Fragment() {

    private var openSession: OpenSession? = null

    //Recycler
    private lateinit var binding: FragmentSessionsBinding
    private lateinit var al_sessions: MutableList<ModelSessionS>
    private lateinit var adapterRSessions: AdapterRSessions

    //Internet
    private lateinit var repository: Repository

    //Add Session
    private lateinit var li_binding: LiAddSessionBinding

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSessionsBinding.inflate(inflater)


        //Add Button
        binding.fsAddSession.setOnClickListener { click_add() }




        //Recycler
        al_sessions = ArrayList()
        adapterRSessions = AdapterRSessions(al_sessions , binding.root.context)
        binding.fsRecyclerSession.setHasFixedSize(true)
        binding.fsRecyclerSession.layoutManager = LinearLayoutManager(binding.root.context)
        binding.fsRecyclerSession.adapter = adapterRSessions

        //Retrofit
        repository = Repository(requireActivity().application as ElRetenApplication)


        loadRecyclerInfo()
        return binding.root
    }


    //Recycler information
    private fun loadRecyclerInfo() {
        lifecycleScope.launch {
            al_sessions = repository.fetchSessionsS(c_drawerS)
            binding.fsRecyclerSession.visibility = View.VISIBLE
            updateRecyclerAdapter()
        }

    }

    private fun updateRecyclerAdapter() {
        if (al_sessions.isNotEmpty()) {
            al_sessions.sortBy { it.c_sessionS }
        }

        if (al_sessions.isEmpty()) {
            binding.fsNotInfoSession.visibility = View.VISIBLE
            binding.fsRecyclerSession.visibility = View.GONE
        } else {
            binding.fsNotInfoSession.visibility = View.GONE
            binding.fsRecyclerSession.visibility = View.VISIBLE
        }
        adapterRSessions = AdapterRSessions(al_sessions , binding.root.context)

        adapterRSessions.setEditListener(object : AdapterRSessions.RecyclerClickListener {
            override fun onClick(position: Int) {
                click_edit(position)
            }
        })
        adapterRSessions.setDeleteListener(object : AdapterRSessions.RecyclerClickListener {
            override fun onClick(position: Int) {
                click_delete(position)
            }
        })
        adapterRSessions.setRecyclerTouchListener(object : AdapterRSessions.RecyclerClickListener {
            override fun onClick(position: Int) {
                val c_sessionS = al_sessions.get(position).c_sessionS
                openSession?.onSessionClicked(c_sessionS)
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
                    } else li_binding.til.error = getString(R.string.No_permitido_simbol)
                } else li_binding.til.error = getString(R.string.Ya_existe_seccion)
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
        lifecycleScope.launch {


            repository.addSessionS("${c_shelfS}_${c_drawerS}_${sessionCode}" , c_drawerS)
        }

        val model = ModelSessionS("${c_shelfS}_${c_drawerS}_${sessionCode}" , c_drawerS, 0)
        al_sessions.add(model)
        updateRecyclerAdapter()
        FancyToast.makeText(
            requireContext() ,
            getString(R.string.Operacion_realizada_con_exito) ,
            FancyToast.LENGTH_LONG ,
            FancyToast.SUCCESS ,
            false
        ).show()

    }

    private fun isDuplicated(name: String): Boolean {
        for (shelf in al_sessions) {
            val positionSymbol = shelf.c_sessionS.lastIndexOf("_")
            val codePrepared = shelf.c_sessionS.substring(positionSymbol + 1)
            if (codePrepared == name) {
                return true
            }
        }
        return false
    }

    //Edit session
    private fun click_edit(position: Int) {
        li_editSession(al_sessions[position].c_sessionS , position)
    }

    private fun li_editSession(codeSessionOld: String , position: Int) {
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
                editSessionInternet(codeSessionOld , tiedContent , position)
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

    private fun editSessionInternet(
        sessionCodeOld: String ,
        sessionCodeNew: String ,
        position: Int
    ) {
        lifecycleScope.launch {
            repository.updateSessionS(
                sessionCodeOld ,
                sessionCodeNew ,
                al_sessions[position].fk_c_drawerS ,
                al_sessions[position].amount
            )
        }

        al_sessions[position].c_sessionS = sessionCodeNew
        updateRecyclerAdapter()
        FancyToast.makeText(
            requireContext() ,
            getString(R.string.Operacion_realizada_con_exito) ,
            FancyToast.LENGTH_LONG ,
            FancyToast.SUCCESS ,
            false
        ).show()
    }

    //Delete session
    private fun click_delete(position: Int) {
        val amount = al_sessions[position].amount
        if (amount == 0) {
            showAlertDialogDeleteSession(position)
        } else {
            showAlertDialogNotEmpty(amount)
        }
    }

    private fun showAlertDialogNotEmpty(amount: Int) {
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setCancelable(true)
            .setTitle(getString(R.string.elemento_no_vaciado))
            .setMessage(getString(R.string.debe_eliminar_todo , amount))
            .setPositiveButton(R.string.Aceptar) { dialog , _ ->
                dialog.dismiss()
            }

        //create the alert dialog and show it
        builder.create().show()
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
        ) { dialog , _ ->
            dialog.dismiss()
            deleteSessionInternet(al_sessions[position].c_sessionS , position)
        }
        builder.setNegativeButton(R.string.No ,
            DialogInterface.OnClickListener { dialog , _ -> dialog.dismiss() })

        //create the alert dialog and show it
        builder.create().show()
    }

    private fun deleteSessionInternet(sessionCode: String , position: Int) {
        lifecycleScope.launch {

            repository.deleteSessionS(
                sessionCode ,
                al_sessions.get(position).fk_c_drawerS
            )
        }

        al_sessions.removeAt(position)
        updateRecyclerAdapter()
        FancyToast.makeText(
            requireContext() ,
            getString(R.string.Operacion_realizada_con_exito) ,
            FancyToast.LENGTH_LONG ,
            FancyToast.SUCCESS ,
            false
        ).show()
    }

    fun setOpenSessionListener(openSession: OpenSession) {
        this.openSession = openSession
    }

    interface OpenSession {
        fun onSessionClicked(c_sessions: String)
    }

}