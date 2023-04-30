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


    class SessionViewHolder(private val binding: RecyclerSessionsBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(model: ModelSession, context: Context) {
            val code = context.getString(R.string.codigo_s, model.code)

            binding.rsTvCodigoSession.text = code
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerSessionsBinding.inflate(layoutInflater, parent, false)
        return SessionViewHolder(binding)
    }

    override fun getItemCount() = al_session.size

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        holder.bind(al_session[position], context)
    }

}