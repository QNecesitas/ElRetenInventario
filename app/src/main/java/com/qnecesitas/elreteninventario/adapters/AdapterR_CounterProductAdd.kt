package com.qnecesitas.elreteninventario.adapters

import android.content.Context
import android.content.ContextWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.qnecesitas.elreteninventario.R
import com.qnecesitas.elreteninventario.data.ModelCart
import com.qnecesitas.elreteninventario.databinding.RecyclerCounterProductAddBinding
import java.io.File

class AdapterR_CounterProductAdd(
    val al_CPShow: MutableList<ModelCart>,
    private val context: Context
) : RecyclerView.Adapter<AdapterR_CounterProductAdd.CounterProductShowViewHolder>() {


    private var cancelListener: RecyclerCancelListener? = null
    

    //Related with holders
    inner class CounterProductShowViewHolder(private val binding: RecyclerCounterProductAddBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            model: ModelCart,
            context: Context,
            cancelListener: RecyclerCancelListener?,
            position: Int
        ) {

            val name = model.product.name
            val amount = context.getString(R.string.s_unidades, model.amount)
            binding.TVName.text = name
            binding.tvCant.text = amount


            val cw = ContextWrapper(context)
            val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
            if(model.product.brand.trim().isEmpty()){

                if(model.product.statePhoto == 0){
                    binding.IVImageProduct.setImageResource(R.drawable.widgets)
                }else{
                    binding.IVImageProduct.setImageBitmap(null)
                    Glide.with(context)
                        .load(File(directory, "${model.product.c_productLS}.jpg"))
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(binding.IVImageProduct)
                }
            }else{
                binding.IVImageProduct.setImageBitmap(null)
                if(model.product.statePhoto==1){
                    Glide.with(context)
                        .load(File(directory, "${model.product.c_productLS}.jpg"))
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(binding.IVImageProduct)
                }
            }

            binding.close.setOnClickListener{
                cancelListener?.onClick(layoutPosition);
            }


        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CounterProductShowViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerCounterProductAddBinding.inflate(layoutInflater, parent, false)
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