package com.qnecesitas.elreteninventario.adapters

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.qnecesitas.elreteninventario.R
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelCart
import com.qnecesitas.elreteninventario.databinding.RecyclerCounterProductShowBinding

class AdapterR_CounterProductAdd(
    val al_CPShow: ArrayList<ModelCart>,
    private val context: Context
) : RecyclerView.Adapter<AdapterR_CounterProductAdd.CounterProductShowViewHolder>() {


    private var cancelListener: RecyclerCancelListener? = null
    

    //Related with holders
    inner class CounterProductShowViewHolder(private val binding: RecyclerCounterProductShowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            model: ModelCart,
            context: Context,
            cancelListener: RecyclerCancelListener?,
            position: Int
        ) {

            val name = model.product.name
            val cantidad = context.getString(R.string.s_unidades, model.product.amount)
            val precio = context.getString(R.string.PrecioV_Info, model.product.salePrice)

            if (model.product.amount == 0){
                binding.tvCant.paintFlags = binding.tvCant.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvCant.setTextColor(ContextCompat.getColor(context, R.color.md_theme_light_surfaceTint))
            }else{
                binding.tvCant.paintFlags = binding.tvCant.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.tvCant.setTextColor(ContextCompat.getColor(context, R.color.md_theme_light_onBackground))
            }
            binding.TVName.text = name
            binding.tvCant.text = cantidad
            binding.tvPrecioV.text = precio
            binding.tvMarcaLittle.text = model.product.brand
            binding.tvMarcaBig.text = model.product.brand



            if(model.product.brand.trim().isEmpty()){
                binding.tvMarcaBig.visibility = View.GONE
                binding.ivDecoration.visibility = View.GONE
                binding.tvMarcaLittle.visibility = View.GONE
                if(model.product.statePhoto == 0){
                    binding.IVImageProduct.setImageResource(R.drawable.widgets)
                }else{
                    binding.IVImageProduct.setImageBitmap(null)
                    Glide.with(context)
                        .load(Constants.PHP_IMAGES + "P_" + model.product.c_productS + ".jpg")
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(binding.IVImageProduct)
                }
            }else{
                binding.IVImageProduct.setImageBitmap(null)
                if(model.product.statePhoto==1){
                    Glide.with(context)
                        .load(Constants.PHP_IMAGES + "P_" + model.product.c_productS + ".jpg")
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(binding.IVImageProduct)
                }
            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CounterProductShowViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerCounterProductShowBinding.inflate(layoutInflater, parent, false)
        return CounterProductShowViewHolder(binding)
    }

    override fun getItemCount() = al_CPShow.size

    override fun onBindViewHolder(holder: CounterProductShowViewHolder, position: Int) {
        holder.bind(
            al_CPShow[position], context, cancelListener, position
        )
    }

    interface RecyclerCancelListener {
        fun onClick(position: Int)
    }

    fun setCancelListener(cancelListener: RecyclerCancelListener) {
        this.cancelListener = cancelListener
    }


}