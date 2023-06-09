package com.qnecesitas.elreteninventario.adapters

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.qnecesitas.elreteninventario.R
import com.qnecesitas.elreteninventario.auxiliary.Constants
import com.qnecesitas.elreteninventario.data.ModelEditProductLS
import com.qnecesitas.elreteninventario.data.ModelEditProductS
import com.qnecesitas.elreteninventario.databinding.RecyclerCounterProductShowBinding
import java.io.File
import java.util.Locale

class AdapterR_CounterProductShow(
        val al_CPShow: MutableList<ModelEditProductLS>,
        private val context: Context
) : RecyclerView.Adapter<AdapterR_CounterProductShow.CounterProductShowViewHolder>() {

    private var alFilter: MutableList<ModelEditProductLS> = mutableListOf()

    private var clickListener: RecyclerClickListener? = null

    private var infoListener: RecyclerClickListener? = null

    private var pathListener: RecyclerClickListener? = null

    private var customFilter: AdapterR_CounterProductShow.CustomFilter? = null

    init {
        alFilter.addAll(al_CPShow)
        customFilter = CustomFilter(this)
    }

    //Related with holders
    inner class CounterProductShowViewHolder(private val binding: RecyclerCounterProductShowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
                model: ModelEditProductLS,
                context: Context,
                clickListener: RecyclerClickListener?,
                infoListener: RecyclerClickListener?,
                pathListener: RecyclerClickListener?,
                position: Int
        ) {

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
            binding.tvMarcaLittle.text = model.brand
            binding.tvMarcaBig.text = model.brand


            val cw = ContextWrapper(context)
            val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
            if(model.brand.trim().isEmpty()){
                binding.tvMarcaBig.visibility = View.GONE
                binding.ivDecoration.visibility = View.GONE
                binding.tvMarcaLittle.visibility = View.GONE
                if(model.statePhoto == 0){
                    binding.IVImageProduct.setImageResource(R.drawable.widgets)
                }else{
                    binding.IVImageProduct.setImageBitmap(null)
                    Glide.with(context)
                        .load(File(directory, "${model.c_productLS}.jpg"))
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(binding.IVImageProduct)
                }
            }else{
                binding.IVImageProduct.setImageBitmap(null)
                if(model.statePhoto==1){
                    Glide.with(context)
                        .load(File(directory, "${model.c_productLS}.jpg"))
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(binding.IVImageProduct)
                }
            }



            binding.tvOptionProduct.setOnClickListener {
                val popupMenu = PopupMenu(context, it)
                popupMenu.menuInflater.inflate(R.menu.menu_counter_product, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.cProd_info -> infoListener?.onClick(getRealPosition(position))
                        R.id.cProd_ubic -> pathListener?.onClick(getRealPosition(position))
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
            alFilter[position], context, clickListener, infoListener, pathListener, position
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

    fun setClickPathListener(pathListener: RecyclerClickListener) {
        this.pathListener = pathListener
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
            if (charSequence.isEmpty()) {
                alFilter.addAll(al_CPShow)
            } else {
                val filterPattern =
                    charSequence.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (product in al_CPShow) {
                    if (product.name.lowercase(Locale.ROOT).trim().contains(filterPattern)) {
                        alFilter.add(product)
                    }else if(product.c_productLS.lowercase(Locale.ROOT).trim().contains(filterPattern)){
                        alFilter.add(product)
                    }else if(product.size.lowercase(Locale.ROOT).trim().contains(filterPattern)){
                        alFilter.add(product)
                    }else if(product.fk_c_sessionLS.lowercase(Locale.ROOT).trim().contains(filterPattern)){
                        alFilter.add(product)
                    }else if(product.brand.lowercase(Locale.ROOT).trim().contains(filterPattern)){
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