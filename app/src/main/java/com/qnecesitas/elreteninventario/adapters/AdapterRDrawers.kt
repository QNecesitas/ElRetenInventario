package com.qnecesitas.elreteninventario.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qnecesitas.elreteninventario.R
import com.qnecesitas.elreteninventario.data.ModelDrawer
import com.qnecesitas.elreteninventario.databinding.RecyclerDrawersBinding


class AdapterRDrawers(private val al_drawers: ArrayList<ModelDrawer>, private val context: Context) :
    RecyclerView.Adapter<AdapterRDrawers.DrawerViewHolder>() {


    private var editListener: RecyclerClickListener? = null
    private var deleteListener: RecyclerClickListener? = null
    private var touchListener: RecyclerClickListener? = null

    class DrawerViewHolder(private val binding: RecyclerDrawersBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(
            model: ModelDrawer ,
            context: Context,
            editListener: RecyclerClickListener?,
            deleteListener: RecyclerClickListener?,
            touchListener: RecyclerClickListener?,
            position: Int
        ) {
            val guionPosition = model.c_drawerS.indexOf("_")
            val originCode = model.c_drawerS.substring(guionPosition + 1)
            val code = context.getString(R.string.codigo_s, originCode)
            val amount = context.getString(R.string.cant_secciones, model.amount.toString())

            binding.rsTvCodigo.text = code
            binding.rsCant.text = amount

            binding.btnDelete.setOnClickListener { deleteListener?.onClick(position) }
            binding.btnEdit.setOnClickListener { editListener?.onClick(position) }
            binding.root.setOnClickListener { touchListener?.onClick(position) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerDrawersBinding.inflate(layoutInflater, parent, false)
        return DrawerViewHolder(binding)
    }

    override fun getItemCount() = al_drawers.size

    override fun onBindViewHolder(holder: DrawerViewHolder, position: Int) {
        holder.bind(al_drawers[position], context, editListener, deleteListener,touchListener, position)
    }

    interface RecyclerClickListener{
        fun onClick(position: Int)
    }

    fun setDeleteListener( deleteListener: RecyclerClickListener){
        this.deleteListener = deleteListener
    }

    fun setEditListener(editListener: RecyclerClickListener){
        this.editListener = editListener
    }

    fun setTouchListener(touchListener: RecyclerClickListener){
        this.touchListener = touchListener
    }



}