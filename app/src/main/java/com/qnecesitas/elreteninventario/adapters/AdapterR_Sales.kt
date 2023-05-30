package com.qnecesitas.elreteninventario.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.qnecesitas.elreteninventario.R
import com.qnecesitas.elreteninventario.data.ModelSale
import com.qnecesitas.elreteninventario.databinding.RecyclerSalesBinding
import java.util.Locale

class AdapterR_Sales(val alSales: ArrayList<ModelSale>, private val context: Context):
    RecyclerView.Adapter<AdapterR_Sales.SalesViewHolder>() {

    private var customFilter: AdapterR_Sales.CustomFilter? = null
    private var alFilter: ArrayList<ModelSale> = ArrayList()
    private var clickClose: ITouchClose? = null

    class SalesViewHolder(private val binding: RecyclerSalesBinding): RecyclerView.ViewHolder(binding.root){
        private var rotationB = false

        fun bind(
            modelSale: ModelSale,
            context: Context,
            position: Int,
            clickCLose: ITouchClose?
        ){
            val c_order = modelSale.c_order
            val nombre = modelSale.name
            val precio = context.getString(R.string.precio_f,modelSale.totalPrice)
            val producto = modelSale.products
            val descuento = modelSale.discount.toString()
            val dia = modelSale.day.toString()
            val mes = modelSale.month.toString()
            val anio = modelSale.year.toString()
            val type = modelSale.type
            val tranfer = modelSale.totalTranf

            binding.tvNumeroVenta.text = c_order.toString()
            binding.tvNombVenta.text = nombre
            binding.tvRebajaVenta.text = descuento
            binding.tvPriceTotalVenta.text = precio
            binding.tvType.text = type
            binding.tvPriceTotalTransferS?.text = tranfer.toString()
            binding.tvOrderVenta.text = producto.replace("--n--", "\n")
                .replace("--s--", "   ")
            binding.tvFechaVenta.text = context.getString(R.string.Fecha,dia,mes,anio)

            binding.ivArrow.setOnClickListener(View.OnClickListener {
                if(!rotationB){
                    binding.ivArrow.rotation = 180F
                    rotationB = true
                    binding.tvOrderVentaStatic.visibility = View.VISIBLE
                    binding.tvOrderVenta.visibility = View.VISIBLE
                    binding.tvPriceTotalVentaS.visibility = View.VISIBLE
                    binding.tvPriceTotalVenta.visibility = View.VISIBLE
                    binding.tvRebajaVentaS.visibility = View.VISIBLE
                    binding.tvRebajaVenta.visibility = View.VISIBLE
                    binding.tvType.visibility = View.VISIBLE
                    binding.tvTypeS.visibility = View.VISIBLE
                    binding.tvPriceTotalTransfer?.visibility = View.VISIBLE
                    binding.tvPriceTotalTransferS?.visibility = View.VISIBLE
                }else{
                    binding.ivArrow.rotation = 0F
                    rotationB = false
                    binding.tvOrderVentaStatic.visibility = View.GONE
                    binding.tvOrderVenta.visibility = View.GONE
                    binding.tvPriceTotalVentaS.visibility = View.GONE
                    binding.tvPriceTotalVenta.visibility = View.GONE
                    binding.tvRebajaVentaS.visibility = View.GONE
                    binding.tvRebajaVenta.visibility = View.GONE
                    binding.tvType.visibility = View.GONE
                    binding.tvTypeS.visibility = View.GONE
                    binding.tvPriceTotalTransfer?.visibility = View.GONE
                    binding.tvPriceTotalTransferS?.visibility = View.GONE
                }
            })

            binding.cvClose.setOnClickListener{ clickCLose?.onClickClose(position) }


        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerSalesBinding.inflate(layoutInflater, parent, false)
        return SalesViewHolder(binding)
    }

    override fun getItemCount() = alFilter.size

    override fun onBindViewHolder(holder: SalesViewHolder, position: Int) {
        holder.bind(alFilter[position],context,position,clickClose)
    }

    init {
        alFilter.addAll(alSales)
        customFilter = CustomFilter(this)
    }


    /*Filter elements
    *------------------
     */
    //Class custom
    inner class CustomFilter(adapterR_sales: AdapterR_Sales) :
        Filter() {
        var adapterR_sales: AdapterR_Sales

        init {
            this.adapterR_sales = adapterR_sales
        }

        override fun performFiltering(charSequence: CharSequence): FilterResults {
            alFilter.clear()
            val filterResults = FilterResults()
            if (charSequence.length == 0) {
                alFilter.addAll(alSales)
            } else {
                val filterPattern =
                    charSequence.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (product in alSales) {
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
            adapterR_sales.notifyDataSetChanged()
        }
    }

    //Real position
    private fun getRealPosition(irealPosition: Int): Int{
        for((index, model) in alSales.withIndex()){
            if(model == alFilter[irealPosition]){
                return index
            }
        }
        return 0
    }

    //Custom
    fun getFilter(): Filter? {
        return customFilter
    }

    //Close
    interface ITouchClose{
        fun onClickClose(position: Int)
    }

    fun setCloseClick(clickClose: ITouchClose){
        this.clickClose = clickClose
    }

}