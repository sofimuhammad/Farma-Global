package com.example.farmaglobal.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.farmaglobal.Network.PasienResponse
import com.example.farmaglobal.R

class ListPasienAdapter(private val listPasien: ArrayList<PasienResponse.Response.Data>) :
    RecyclerView.Adapter<ListPasienAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvItemName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvItemNumber: TextView = itemView.findViewById(R.id.tv_item_number)
        var tvItemAddress: TextView = itemView.findViewById(R.id.tv_item_address)
    }

    private lateinit var onClick: (PasienResponse.Response.Data) -> Unit

    fun onClickItem(onClick: (PasienResponse.Response.Data) -> Unit) {
        this.onClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
    val view: View =
        LayoutInflater.from(parent.context).inflate(R.layout.item_row_pasien, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val pasien = listPasien[position]
        holder.tvItemName.text = pasien.patientName
        holder.tvItemNumber.text = pasien.patientId
        holder.tvItemAddress.text = pasien.address

        holder.itemView.setOnClickListener {
            onClick(pasien)
        }
    }

    override fun getItemCount(): Int {
        return listPasien.size
    }
}