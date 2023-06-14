package com.qnecesitas.elreteninventario.adapters

import android.content.Context
import android.content.ContextWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.qnecesitas.elreteninventario.R
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelEditProductLS
import com.qnecesitas.elreteninventario.data.ModelEditProductS
import com.qnecesitas.elreteninventario.databinding.RecyclerDeficitBinding
import java.io.File

class AdapterR_DeficitProduct(
        private val al_deficitProdut: MutableList<ModelEditProductS>,
        private val context: Context
)  : RecyclerView.Adapter<AdapterR_DeficitProduct.DeficitProductViewHolder>(){


     private var recyclerClickListener: RecyclerClickListener? = null

    //Related with Holders
    class DeficitProductViewHolder(private val binding: RecyclerDeficitBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(
                model: ModelEditProductS,
                context: Context,
                recyclerClickListener: RecyclerClickListener?
        ) {
            val cw = ContextWrapper(context)
            val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
            Glide.with(context)
                    .load(File(directory, "${model.c_productS}.jpg"))
                    .error(R.drawable.widgets)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(binding.rdIvIcon)

            val nombre = model.name
            val size = context.getString(R.string.Size_Info, model.size)
            val cantidad = context.getString(R.string.s_unidades, model.amount)

            binding.rdTvName.text = nombre
            binding.rdTvSize.text = size
            binding.rdTvCantP.text = cantidad
            binding.root.setOnClickListener{
                recyclerClickListener?.onClick(model)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeficitProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerDeficitBinding.inflate(layoutInflater, parent, false)
        return DeficitProductViewHolder(binding)
    }

    override fun getItemCount() = al_deficitProdut.size

    override fun onBindViewHolder(holder: DeficitProductViewHolder, position: Int) {
        holder.bind(al_deficitProdut[position], context, recyclerClickListener)
    }


    fun setRecyclerClickListener(listener: RecyclerClickListener){
        this.recyclerClickListener = listener
    }

    interface RecyclerClickListener{
        fun onClick(modelS: ModelEditProductS)
    }


}