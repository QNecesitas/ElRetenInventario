package com.qnecesitas.elreteninventario.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.qnecesitas.elreteninventario.R
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelCart
import com.qnecesitas.elreteninventario.data.ModelEditProduct
import com.qnecesitas.elreteninventario.databinding.RecyclerCounterProductAddBinding

class AdapterR_CounterProductAdd(
    val al_CPAdd: ArrayList<ModelCart>,
    private val context: Context
) : RecyclerView.Adapter<AdapterR_CounterProductAdd.CounterProductAddViewHolder>()  {

    private var cancelListener: RecyclerClickListener? = null

    //Related with holders
    class CounterProductAddViewHolder(private val binding: RecyclerCounterProductAddBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            model: ModelCart,
            context: Context,
            cancelListener: RecyclerClickListener?,
            position: Int
        ){
            Glide.with(context)
                .load((Constants.PHP_IMAGES + "P_" + position) + ".jpg")
                .error(R.drawable.widgets)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.IVImageProduct)

            val name = model.product.name
            val cantidad = context.getString(R.string.s_unidades, model.amout)

            binding.TVName.text = name
            binding.tvCant.text = cantidad

            binding.close.setOnClickListener {  cancelListener?.onClick(position) }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CounterProductAddViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerCounterProductAddBinding.inflate(layoutInflater, parent, false)
        return CounterProductAddViewHolder(binding)
    }

    override fun getItemCount() = al_CPAdd.size

    override fun onBindViewHolder(holder: CounterProductAddViewHolder, position: Int) {
        holder.bind(al_CPAdd[position], context, cancelListener ,position)
    }

    interface RecyclerClickListener{
        fun onClick(position: Int)
    }

    fun setCancelListener(cancelListener: RecyclerClickListener?){
        this.cancelListener = cancelListener
    }

}