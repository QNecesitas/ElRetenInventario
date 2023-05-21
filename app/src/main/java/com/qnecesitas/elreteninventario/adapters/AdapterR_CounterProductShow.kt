package com.qnecesitas.elreteninventario.adapters

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.qnecesitas.elreteninventario.R
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelEditProduct
import com.qnecesitas.elreteninventario.databinding.RecyclerCounterProductShowBinding
import java.util.Locale

class AdapterR_CounterProductShow(
    val al_CPShow: ArrayList<ModelEditProduct>,
    private val context: Context
) : RecyclerView.Adapter<AdapterR_CounterProductShow.CounterProductShowViewHolder>() {

    private var alFilter: ArrayList<ModelEditProduct> = ArrayList()

    private var clickListener: RecyclerClickListener? = null

    private var infoListener: RecyclerClickListener? = null

    private var customFilter: AdapterR_CounterProductShow.CustomFilter? = null

    init {
        alFilter.addAll(al_CPShow)
        customFilter = CustomFilter(this)
    }

    //Related with holders
    inner class CounterProductShowViewHolder(private val binding: RecyclerCounterProductShowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            model: ModelEditProduct,
            context: Context,
            clickListener: RecyclerClickListener?,
            infoListener: RecyclerClickListener?,
            position: Int
        ) {
            Glide.with(context)
                .load((Constants.PHP_IMAGES + "P_" + position) + ".jpg")
                .error(R.drawable.widgets)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.IVImageProduct)

            val name = model.name
            val cantidad = context.getString(R.string.s_unidades, model.amount)
            val precio = context.getString(R.string.PrecioV_Info, model.salePrice)

            if (model.amount == 0){
                binding.tvCant.paintFlags = binding.tvCant.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvCant.setTextColor(ContextCompat.getColor(context, R.color.md_theme_light_surfaceTint))
            }else{
                binding.tvCant.paintFlags = binding.tvCant.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.tvCant.setTextColor(ContextCompat.getColor(context, R.color.md_theme_light_onBackground))
            }
            binding.TVName.text = name
            binding.tvCant.text = cantidad
            binding.tvPrecioV.text = precio


            binding.tvOptionProduct.setOnClickListener {
                val popupMenu = PopupMenu(context, it)
                popupMenu.menuInflater.inflate(R.menu.menu_counter_product, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.cProd_info -> infoListener?.onClick(getRealPosition(position))
                    }
                    false
                }
                popupMenu.show() }

            binding.root.setOnClickListener { clickListener?.onClick(getRealPosition(position)) }

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

    override fun getItemCount() = alFilter.size

    override fun onBindViewHolder(holder: CounterProductShowViewHolder, position: Int) {
        holder.bind(
            alFilter[position], context, clickListener, infoListener, position
        )
    }

    interface RecyclerClickListener {
        fun onClick(position: Int)
    }

    fun setClickListener(clickListener: RecyclerClickListener) {
        this.clickListener = clickListener
    }

    fun setClickInfoListener(infoListener: RecyclerClickListener) {
        this.infoListener = infoListener
    }



    /*Filter elements
    *------------------
     */
    fun getFilter(): Filter? {
        return customFilter
    }
    
    inner class CustomFilter(adapterR_CounterProductShow: AdapterR_CounterProductShow) :
        Filter() {
        private var adapterR_CounterProductShow: AdapterR_CounterProductShow

        init {
            this.adapterR_CounterProductShow = adapterR_CounterProductShow
        }

        override fun performFiltering(charSequence: CharSequence): FilterResults {
            alFilter.clear()
            val filterResults = FilterResults()
            if (charSequence.length == 0) {
                alFilter.addAll(al_CPShow)
            } else {
                val filterPattern =
                    charSequence.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (product in al_CPShow) {
                    if (product.name.lowercase(Locale.ROOT).trim().contains(filterPattern)) {
                        alFilter.add(product)
                    }else if(product.c_productS.lowercase(Locale.ROOT).trim().contains(filterPattern)){
                        alFilter.add(product)
                    }else if(product.size.lowercase(Locale.ROOT).trim().contains(filterPattern)){
                        alFilter.add(product)
                    }else if(product.fk_c_sessionS.lowercase(Locale.ROOT).trim().contains(filterPattern)){
                        alFilter.add(product)
                    }
                }
            }
            filterResults.values = alFilter
            filterResults.count = alFilter.size
            return filterResults
        }

        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            adapterR_CounterProductShow.notifyDataSetChanged()
        }
    }


    /*Real position
    *------------------------------
     */
    private fun getRealPosition(irealPosition: Int): Int{
        for((index, model) in al_CPShow.withIndex()){
            if(model == alFilter[irealPosition]){
                return index
            }
        }
        return 0
    }

}