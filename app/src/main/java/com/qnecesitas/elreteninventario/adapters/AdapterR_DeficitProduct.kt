package com.qnecesitas.elreteninventario.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.qnecesitas.elreteninventario.R
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelEditProduct
import com.qnecesitas.elreteninventario.databinding.RecyclerDeficitBinding
import java.util.Locale

class AdapterR_DeficitProduct(
    private val al_deficitProdut: ArrayList<ModelEditProduct>,
    private val context: Context
)  : RecyclerView.Adapter<AdapterR_DeficitProduct.DeficitProductViewHolder>(){

    private var al_filter: ArrayList<ModelEditProduct> = ArrayList()


    //Related with Holders
    class DeficitProductViewHolder(private val binding: RecyclerDeficitBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(
            model: ModelEditProduct,
            context: Context,
            position: Int
        ) {
            Glide.with(context)
                .load((Constants.PHP_IMAGES + "Producto_" + position) + ".jpg")
                .error(R.drawable.shopping_bag_white)
                .skipMemoryCache(true)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.rdIvIcon)

            val nombre = model.name
            val cantidad = context.getString(R.string.s_unidades, model.amount)

            binding.rdTvName.text = nombre
            binding.rdTvCantP.text = cantidad

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeficitProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerDeficitBinding.inflate(layoutInflater, parent, false)
        return DeficitProductViewHolder(binding)
    }

    override fun getItemCount() = al_filter.size

    override fun onBindViewHolder(holder: DeficitProductViewHolder, position: Int) {
        holder.bind(al_deficitProdut[position], context, position)
    }




}