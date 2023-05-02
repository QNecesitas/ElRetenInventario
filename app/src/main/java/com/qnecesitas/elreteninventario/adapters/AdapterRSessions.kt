package com.qnecesitas.elreteninventario.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qnecesitas.elreteninventario.R
import com.qnecesitas.elreteninventario.data.ModelSession
import com.qnecesitas.elreteninventario.databinding.RecyclerSessionsBinding

class AdapterRSessions(private val al_session: ArrayList<ModelSession>, private val context: Context) :
    RecyclerView.Adapter<AdapterRSessions.SessionViewHolder>() {

    private var editListener: RecyclerClickListener? = null
    private var deleteListener: RecyclerClickListener? = null
    private var touchListener: RecyclerClickListener? = null

    class SessionViewHolder(private val binding: RecyclerSessionsBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(
            model: ModelSession,
            context: Context,
            editListener: RecyclerClickListener?,
            deleteListener: RecyclerClickListener?,
            touchListener: RecyclerClickListener?,
            position: Int
        ) {
            val code = context.getString(R.string.codigo_s, model.c_sessionS)
            val amount = context.getString(R.string.cant_producto, model.amount.toString())

            binding.rsTvCodigoSession.text = code
            binding.rsCantSession.text = amount

            binding.btnEdit.setOnClickListener { editListener?.onClick(position)}
            binding.btnDelete.setOnClickListener { deleteListener?.onClick(position)}
            binding.root.setOnClickListener { touchListener?.onClick(position) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerSessionsBinding.inflate(layoutInflater, parent, false)
        return SessionViewHolder(binding)
    }

    override fun getItemCount() = al_session.size

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        holder.bind(al_session[position], context, editListener, deleteListener,touchListener, position)
    }


    interface RecyclerClickListener {
        fun onClick(position: Int);
    }

    fun setEditListener(editListener: RecyclerClickListener) {
        this.editListener = editListener
    }

    fun setDeleteListener(deleteListener: RecyclerClickListener) {
        this.deleteListener = deleteListener
    }

    fun setRecyclerTouchListener(touchListener: RecyclerClickListener) {
        this.touchListener = touchListener
    }

}