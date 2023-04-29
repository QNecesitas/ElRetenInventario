package com.qnecesitas.elreteninventario.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qnecesitas.elreteninventario.R
import com.qnecesitas.elreteninventario.data.ModelShelf
import com.qnecesitas.elreteninventario.databinding.RecyclerShelvesBinding


class AdapterRShelves(private val al_shelves: ArrayList<ModelShelf>, private val context: Context) :
    RecyclerView.Adapter<AdapterRShelves.ShelvesViewHolder>() {

    private var editListener: RecyclerClickListener? = null
    private var deleteListener: RecyclerClickListener? = null
    private var touchListener: RecyclerClickListener? = null


    class ShelvesViewHolder(private val binding: RecyclerShelvesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            model: ModelShelf,
            context: Context,
            editListener: RecyclerClickListener?,
            deleteListener: RecyclerClickListener?,
            touchListener: RecyclerClickListener?,
            position: Int
        ) {
            val code = context.getString(R.string.codigo_s, model.c_shelfS)
            val cant = context.getString(R.string.cant_gavetas, model.amount.toString())

            binding.rsTvCodigo.text = code
            binding.rsCant.text = cant

            binding.btnEdit.setOnClickListener { editListener?.onClick(position)}
            binding.btnDelete.setOnClickListener { deleteListener?.onClick(position)}
            binding.root.setOnClickListener { touchListener?.onClick(position) }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShelvesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerShelvesBinding.inflate(layoutInflater, parent, false)
        return ShelvesViewHolder(binding)
    }

    override fun getItemCount() = al_shelves.size

    override fun onBindViewHolder(holder: ShelvesViewHolder, position: Int) {
        holder.bind(al_shelves[position], context, editListener, deleteListener,touchListener, position)
    }

    interface RecyclerClickListener {
        fun onClick(position: Int);
    }

    fun setEditListener(editListener: RecyclerClickListener) {
        this.editListener = editListener
    }

    fun setDeleteListener(deleteListener: RecyclerClickListener) {
        this.deleteListener = deleteListener
    }

    fun setRecyclerTouchListener(touchListener: RecyclerClickListener) {
        this.touchListener = touchListener
    }



}