package com.qnecesitas.elreteninventario.adapters

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.qnecesitas.elreteninventario.R
import com.qnecesitas.elreteninventario.data.ModelSale
import com.qnecesitas.elreteninventario.databinding.RecyclerSalesBinding
import java.util.Locale

class AdapterR_Sales(val al_sales: ArrayList<ModelSale>, private val context: Context):
    RecyclerView.Adapter<AdapterR_Sales.SalesViewHolder>() {

    private var customFilter: AdapterR_Sales.CustomFilter? = null
    private var alFilter: ArrayList<ModelSale> = ArrayList()

    class SalesViewHolder(private val binding: RecyclerSalesBinding): RecyclerView.ViewHolder(binding.root){
        private var rotationB = false

        fun bind(
            modelSale: ModelSale,
            context: Context,
            position: Int
        ){
            val c_order = modelSale.c_order
            val nombre = modelSale.name
            val precio = modelSale.totalPrice.toString()
            val producto = modelSale.products
            val descuento = modelSale.discount.toString()
            val dia = modelSale.day.toString()
            val mes = modelSale.month.toString()
            val anio = modelSale.year.toString()

            binding.tvNumeroVenta.text = c_order.toString()
            binding.tvNombVenta.text = context.getString(R.string.nombre,nombre)
            binding.tvRebajaVenta.text = context.getString(R.string.rebaja,descuento)
            binding.tvPriceTotalVenta.text = context.getString(R.string.precioTotal,precio)
            binding.tvOrderVenta.text = context.getString(R.string.Orden,producto)
            binding.tvFechaVenta.text = context.getString(R.string.Fecha,dia,mes,anio)

            binding.ivArrow.setOnClickListener(View.OnClickListener {
                if(!rotationB){
                    binding.ivArrow.rotation = 180F
                    rotationB = true
                    binding.tvOrderVenta.visibility = View.VISIBLE
                    binding.tvPriceTotalVenta.visibility = View.VISIBLE
                    binding.tvRebajaVenta.visibility = View.VISIBLE
                }else{
                    binding.ivArrow.rotation = 0F
                    rotationB = false
                    binding.tvOrderVenta.visibility = View.GONE
                    binding.tvPriceTotalVenta.visibility = View.GONE
                    binding.tvRebajaVenta.visibility = View.GONE
                }
            })

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerSalesBinding.inflate(layoutInflater, parent, false)
        return SalesViewHolder(binding)
    }

    override fun getItemCount() = al_sales.size

    override fun onBindViewHolder(holder: SalesViewHolder, position: Int) {
        holder.bind(al_sales[position],context,position)
    }

    //Arrow


    /*Filter elements
    *------------------
     */
    fun getFilter(): Filter? {
        return customFilter
    }

    inner class CustomFilter(adapterRSales: AdapterR_Sales) :
        Filter() {
        private var adapterRSales: AdapterR_Sales

        init {
            this.adapterRSales = adapterRSales
        }

        override fun performFiltering(charSequence: CharSequence): FilterResults {
            alFilter.clear()
            val filterResults = FilterResults()
            if (charSequence.length == 0) {
                alFilter.addAll(al_sales)
            } else {
                val filterPattern =
                    charSequence.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (product in al_sales) {
                    if (product.name.lowercase(Locale.ROOT).trim().contains(filterPattern)) {
                        alFilter.add(product)
                    }
                }
            }
            filterResults.values = alFilter
            filterResults.count = alFilter.size
            return filterResults
        }

        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            adapterRSales.notifyDataSetChanged()
        }
    }

}