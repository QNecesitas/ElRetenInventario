package com.qnecesitas.elreteninventario.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qnecesitas.elreteninventario.R
import com.qnecesitas.elreteninventario.data.ModelDrawer
import com.qnecesitas.elreteninventario.data.ModelShelf
import com.qnecesitas.elreteninventario.databinding.RecyclerDrawersBinding
import com.qnecesitas.elreteninventario.databinding.RecyclerShelvesBinding


class AdapterRDrawers(private val al_drawers: ArrayList<ModelDrawer>, private val context: Context) :
    RecyclerView.Adapter<AdapterRDrawers.DrawerViewHolder>() {


    class DrawerViewHolder(private val binding: RecyclerDrawersBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(model: ModelDrawer , context: Context) {
            val code = context.getString(R.string.codigo_s, model.code)

            binding.rsTvCodigo.text = code
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerDrawersBinding.inflate(layoutInflater, parent, false)
        return DrawerViewHolder(binding)
    }

    override fun getItemCount() = al_drawers.size

    override fun onBindViewHolder(holder: DrawerViewHolder, position: Int) {
        holder.bind(al_drawers[position], context)
    }

}