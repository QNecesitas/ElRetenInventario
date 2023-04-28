package com.qnecesitas.elreteninventario.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qnecesitas.elreteninventario.data.ModelEditProduct
import com.qnecesitas.elreteninventario.databinding.RecyclerEditProductBinding


class AdapterR_EditProduct(private val al_editProdut: ArrayList<ModelEditProduct>, private val context: Context) :
    RecyclerView.Adapter<AdapterR_EditProduct.EditProductViewHolder>() {

    private var touch: RecyclerClickListener? = null

    class EditProductViewHolder(private val binding: RecyclerEditProductBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(
            model: ModelEditProduct,
            context: Context,
            position: Int
        ) {
            val nombre = model.name
            val precio = model.price
            val desc = model.desc

            binding.REPTVName.text = nombre
            binding.REPTVPrice.text = precio.toString()
            binding.REPTVDescProduct.text = desc

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerEditProductBinding.inflate(layoutInflater, parent, false)
        return EditProductViewHolder(binding)
    }

    override fun getItemCount() = al_editProdut.size

    override fun onBindViewHolder(holder: EditProductViewHolder, position: Int) {
        holder.bind(al_editProdut[position], context, position)
    }

    interface RecyclerClickListener {
        fun onClick(position: Int);
    }

    fun touchEditListener(touch: AdapterR_EditProduct.RecyclerClickListener) {
        this.touch = touch
    }

}