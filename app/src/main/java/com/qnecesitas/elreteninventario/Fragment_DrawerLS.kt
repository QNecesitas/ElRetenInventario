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
import com.qnecesitas.elreteninventario.adapters.AdapterRDrawers
import com.qnecesitas.elreteninventario.adapters.AdapterRDrawersLS
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelDrawerLS
import com.qnecesitas.elreteninventario.data.ModelDrawerS
import com.qnecesitas.elreteninventario.database.Repository
import com.qnecesitas.elreteninventario.databinding.FragmentDrawerLsBinding
import com.qnecesitas.elreteninventario.databinding.LiAddDrawerBinding
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Fragment_DrawerLS(var c_shelfLS: String) : Fragment() {
    private var openDrawer: OpenDrawerLS? = null

    //Recycler
    private lateinit var binding: FragmentDrawerLsBinding
    private lateinit var al_drawerLS: MutableList<ModelDrawerLS>
    private lateinit var adapterRDrawersLS: AdapterRDrawersLS

    //Internet
    private lateinit var repository: Repository

    //Add Drawer
    private lateinit var li_binding: LiAddDrawerBinding


    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDrawerLsBinding.inflate(inflater)


        //Add Button
        binding.fdAdd.setOnClickListener { click_add() }

        //Refresh
        binding.refresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                loadRecyclerInfo()
            }
        })

        //Recycler
        al_drawerLS = ArrayList()
        adapterRDrawersLS = AdapterRDrawersLS(al_drawerLS , binding.root.context)
        binding.fdRecycler.setHasFixedSize(true)
        binding.fdRecycler.layoutManager = LinearLayoutManager(binding.root.context)
        binding.fdRecycler.adapter = adapterRDrawersLS

        //Retrofit
        repository = Repository(requireActivity().application as ElRetenApplication)

        //Internet
        loadRecyclerInfo()
        return binding.root
    }

    //Recycler information
    private fun loadRecyclerInfo() {
        lifecycleScope.launch {

            al_drawerLS = repository.fetchDrawersLS(c_shelfLS)
            binding.fdRecycler.visibility = View.VISIBLE
            binding.fdNotInfo.visibility = View.GONE
            updateRecyclerAdapter()
        }
    }

    private fun updateRecyclerAdapter() {
        if (al_drawerLS.isNotEmpty()) {
            al_drawerLS.sortBy { it.c_drawerLS }
        }

        if (al_drawerLS.isEmpty()) {
            binding.fdNotInfo.visibility = View.VISIBLE
            binding.fdRecycler.visibility = View.GONE
        } else {
            binding.fdNotInfo.visibility = View.GONE
            binding.fdRecycler.visibility = View.VISIBLE
        }
        adapterRDrawersLS = AdapterRDrawersLS(al_drawerLS , binding.root.context)

        adapterRDrawersLS.setEditListener(object : AdapterRDrawersLS.RecyclerClickListener {
            override fun onClick(position: Int) {
                click_edit(position)
            }
        })
        adapterRDrawersLS.setDeleteListener(object : AdapterRDrawersLS.RecyclerClickListener {
            override fun onClick(position: Int) {
                click_delete(position)
            }
        })
        adapterRDrawersLS.setTouchListener(object : AdapterRDrawersLS.RecyclerClickListener {
            override fun onClick(position: Int) {
                val c_drawer = al_drawerLS.get(position).c_drawerLS
                openDrawer?.onDrawerLSClicked(c_drawer)
            }

        })
        binding.fdRecycler.adapter = adapterRDrawersLS
    }

    //Add Drawer
    private fun click_add() {
        li_newDrawer()
    }

    private fun li_newDrawer() {
        val inflater = LayoutInflater.from(binding.root.context)
        li_binding = LiAddDrawerBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_binding.root)
        val alertDialog = builder.create()
        var tietContent: String;

        li_binding.btnAccept.setOnClickListener {
            tietContent = li_binding.tiet.text.toString()
            if (tietContent.trim().isNotEmpty()) {
                if (!isDuplicated(tietContent)) {
                    if (!tietContent.contains("_")) {
                        addNewDrawerInternet(tietContent)
                        alertDialog.dismiss()
                    } else li_binding.til.error = getString(R.string.No_permitido_simbol)
                } else li_binding.til.error = getString(R.string.Ya_existe_gaveta)
            } else li_binding.til.error = getString(R.string.este_campo_no_debe_vacio)
        }
        li_binding.btnCancel.setOnClickListener { alertDialog.dismiss() }

        //Finalizado
        builder.setCancelable(true)
        alertDialog.window!!.setGravity(Gravity.CENTER)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()


    }

    private fun addNewDrawerInternet(drawerCode: String) {
        lifecycleScope.launch {

            repository.addDrawerLs(c_shelfLS , drawerCode)
        }

        val model = ModelDrawerLS(drawerCode , c_shelfLS)
        al_drawerLS.add(model)
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
        for (shelf in al_drawerLS) {
            val positionSymbol = shelf.c_drawerLS.lastIndexOf("_")
            val codePrepared = shelf.c_drawerLS.substring(positionSymbol + 1)
            if (codePrepared == name) {
                return true
            }
        }
        return false
    }

    //Edit drawer
    private fun click_edit(position: Int) {
        li_editDrawer(al_drawerLS[position].c_drawerLS , position)
    }

    private fun li_editDrawer(codeDrawerOld: String , position: Int) {
        val inflater = LayoutInflater.from(binding.root.context)
        li_binding = LiAddDrawerBinding.inflate(inflater)
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(li_binding.root)
        val alertDialog = builder.create()
        var tiedContent: String;

        val guionPosition = codeDrawerOld.lastIndexOf("_")
        val newCode = codeDrawerOld.substring(guionPosition + 1)
        li_binding.tiet.setText(newCode)
        li_binding.btnAccept.setOnClickListener {
            tiedContent = li_binding.tiet.text.toString()
            if (tiedContent.trim().isNotEmpty()) {
                editDrawerInternet(codeDrawerOld , tiedContent , position)
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


    private fun editDrawerInternet(drawerCodeOld: String , drawerCodeNew: String , position: Int) {
        lifecycleScope.launch {

            repository.updateDrawerLS(
                drawerCodeOld ,
                drawerCodeNew ,
                al_drawerLS[position].fk_c_shelfLS ,
                al_drawerLS[position].amount
            )
        }
        al_drawerLS[position].c_drawerLS = drawerCodeNew
        updateRecyclerAdapter()
        FancyToast.makeText(
            requireContext() ,
            getString(R.string.Operacion_realizada_con_exito) ,
            FancyToast.LENGTH_LONG ,
            FancyToast.SUCCESS ,
            false
        ).show()
    }


    //Delete drawer
    private fun click_delete(position: Int) {
        val amount = al_drawerLS[position].amount
        if (amount == 0) {
            showAlertDialogDeleteDrawer(position)
        } else {
            showAlertDialogNotEmpty(amount)
        }
    }

    private fun showAlertDialogDeleteDrawer(position: Int) {
        //init alert dialog
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setCancelable(true)
        builder.setTitle(R.string.Eliminar_elemento)
        builder.setMessage(R.string.Desea_eliminar_la_gaveta)
        //set listeners for dialog buttons
        builder.setPositiveButton(
            R.string.Si
        ) { dialog , _ ->
            dialog.dismiss()
            deleteDrawerInternet(al_drawerLS[position].c_drawerLS , position)

        }
        builder.setNegativeButton(R.string.No ,
            DialogInterface.OnClickListener { dialog , _ -> dialog.dismiss() })

        //create the alert dialog and show it
        builder.create().show()
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

    private fun deleteDrawerInternet(drawerCode: String , position: Int) {
        lifecycleScope.launch {

            repository.deleteDrawerLS(drawerCode , al_drawerLS.get(position).fk_c_shelfLS)
        }
        al_drawerLS.removeAt(position)
        updateRecyclerAdapter()
        FancyToast.makeText(
            requireContext() ,
            getString(R.string.Operacion_realizada_con_exito) ,
            FancyToast.LENGTH_LONG ,
            FancyToast.SUCCESS ,
            false
        ).show()
    }

    fun setOpenDrawerLSListener(openDrawerLS: OpenDrawerLS) {
        this.openDrawer = openDrawerLS
    }

    interface OpenDrawerLS {
        fun onDrawerLSClicked(code: String)
    }
}