package com.qnecesitas.elreteninventario.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
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
    private val context: Context,
    private val isContracted: Boolean,
    private val isAllInto: Boolean
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
    inner class EditProductViewHolder(private val binding: RecyclerEditProductBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(
            model: ModelEditProduct,
            context: Context,
            position: Int
        ) {

            val name = model.name
            val cant = context.getString(R.string.s_unidades, model.amount)
            val size = model.size
            val brand = model.brand


            binding.REPTVName.text = name
            binding.REPTVCant.text = cant
            binding.REPTVSize.text = size
            binding.root.setOnClickListener{
                this@AdapterR_EditProduct.listener?.onClick(getRealPosition(position))
            }
            binding.tvMarcaBig.text = brand
            binding.tvMarcaLittle.text = brand

            if(isContracted){
                binding.REPIVImageProduct.visibility = View.GONE
                binding.tvMarcaBig.visibility = View.GONE
                binding.ivDecoration.visibility = View.GONE
                binding.tvMarcaLittle.visibility = View.VISIBLE
            }else{
                binding.REPIVImageProduct.visibility = View.VISIBLE
                if(model.statePhoto == 1){
                    binding.tvMarcaBig.visibility = View.GONE
                    binding.ivDecoration.visibility = View.GONE
                    binding.tvMarcaLittle.visibility = View.VISIBLE
                }else if(model.statePhoto == 0){
                    binding.tvMarcaBig.visibility = View.VISIBLE
                    binding.ivDecoration.visibility = View.VISIBLE
                    binding.tvMarcaLittle.visibility = View.GONE
                }
            }

            if(isAllInto){
                binding.ivLocation.visibility = View.VISIBLE
                if(model.fk_c_sessionS.contains(Constants.KEY_SALESPERSON_PRODUCT)){
                    binding.ivLocation.setImageResource(R.drawable.baseline_account_circle_24)
                }else{
                    binding.ivLocation.setImageResource(R.drawable.baseline_admin_panel_settings_24)
                }
            }else{
                binding.ivLocation.visibility = View.GONE
            }

            if(model.brand.trim().isEmpty()){
                binding.tvMarcaBig.visibility = View.GONE
                binding.ivDecoration.visibility = View.GONE
                binding.tvMarcaLittle.visibility = View.GONE
                if(model.statePhoto == 0){
                    binding.REPIVImageProduct.setImageResource(R.drawable.widgets)
                }else{
                    binding.REPIVImageProduct.setImageBitmap(null)
                    Glide.with(context)
                        .load(Constants.PHP_IMAGES + "P_" + model.c_productS + ".jpg")
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(binding.REPIVImageProduct)
                }
            }else{
                binding.REPIVImageProduct.setImageBitmap(null)
                if(model.statePhoto==1){
                    Glide.with(context)
                        .load(Constants.PHP_IMAGES + "P_" + model.c_productS + ".jpg")
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(binding.REPIVImageProduct)
                }
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerEditProductBinding.inflate(layoutInflater, parent, false)
        return EditProductViewHolder(binding)
    }

    override fun getItemCount() = al_filter.size

    override fun onBindViewHolder(holder: EditProductViewHolder, position: Int) {
        holder.bind(al_filter[position], context, position)
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
                    }else if(product.c_productS.lowercase(Locale.ROOT).trim().contains(filterPattern)){
                        al_filter.add(product)
                    }else if(product.size.lowercase(Locale.ROOT).trim().contains(filterPattern)){
                        al_filter.add(product)
                    }else if(product.fk_c_sessionS.lowercase(Locale.ROOT).trim().contains(filterPattern)){
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

    //Real position
    private fun getRealPosition(irealPosition: Int): Int{
        for((index, model) in al_editProdut.withIndex()){
            if(model == al_filter[irealPosition]){
                return index
            }
        }
        return 0
    }


}