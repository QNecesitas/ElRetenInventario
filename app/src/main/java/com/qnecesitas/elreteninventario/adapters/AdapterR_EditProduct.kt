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
import com.qnecesitas.elreteninventario.databinding.RecyclerEditProductBinding
import java.util.Locale

class AdapterR_EditProduct(
    private val al_editProdut: ArrayList<ModelEditProduct>,
    private val context: Context
) :
    RecyclerView.Adapter<AdapterR_EditProduct.EditProductViewHolder>() {

    private var listener: RecyclerClickListener? = null
    private var al_filter: ArrayList<ModelEditProduct> = ArrayList()

    //Custom
    private var customFilter: CustomFilter? = null
    fun getFilter(): Filter? {
        return customFilter
    }

    init {
        al_filter.addAll(al_editProdut)
        customFilter = CustomFilter(this)
    }

    //Related with Holders
    class EditProductViewHolder(private val binding: RecyclerEditProductBinding) :
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
                .into(binding.REPIVImageProduct)

            val nombre = model.name
            val cantidad = context.getString(R.string.s_unidades, model.amount)

            binding.REPTVName.text = nombre
            binding.REPTVCant.text = cantidad

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerEditProductBinding.inflate(layoutInflater, parent, false)
        return EditProductViewHolder(binding)
    }

    override fun getItemCount() = al_filter.size

    override fun onBindViewHolder(holder: EditProductViewHolder, position: Int) {
        holder.bind(al_editProdut[position], context, position)
    }

    fun setRecyclerOnClickListener(listener: RecyclerClickListener) {
        this.listener = listener
    }

    //Recycler listener
    interface RecyclerClickListener {
        fun onClick(position: Int);
    }

    //Class custom
    inner class CustomFilter(adapterR_editProducts: AdapterR_EditProduct) :
        Filter() {
        var adapterR_editProducts: AdapterR_EditProduct

        init {
            this.adapterR_editProducts = adapterR_editProducts
        }

        override fun performFiltering(charSequence: CharSequence): FilterResults {
            al_filter.clear()
            val filterResults = FilterResults()
            if (charSequence.length == 0) {
                al_filter.addAll(al_editProdut)
            } else {
                val filterPattern =
                    charSequence.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (product in al_editProdut) {
                    if (product.name.lowercase(Locale.ROOT).trim().contains(filterPattern)) {
                        al_filter.add(product)
                    }
                }
            }
            filterResults.values = al_filter
            filterResults.count = al_filter.size
            return filterResults
        }

        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            adapterR_editProducts.notifyDataSetChanged()
        }
    }
}