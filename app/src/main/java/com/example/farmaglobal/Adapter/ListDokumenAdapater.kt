package com.example.farmaglobal.Adapter

import android.annotation.SuppressLint
import android.os.TestLooperManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.farmaglobal.Network.DocumentResponse
import com.example.farmaglobal.Network.PasienResponse
import com.example.farmaglobal.Preferences
import com.example.farmaglobal.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListDokumenAdapater(private val listDokumen: ArrayList<DocumentResponse.Response.Data>) :
    RecyclerView.Adapter<ListDokumenAdapater.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivDoc: ImageView = itemView.findViewById(R.id.iv_doc)
        var tvNamaDoc: TextView = itemView.findViewById(R.id.tv_nama_doc)
        var tvTglDoc: TextView = itemView.findViewById(R.id.tv_tgl_doc)
    }

    private lateinit var onClick: (DocumentResponse.Response.Data) -> Unit

    fun onClickItem(onClick: (DocumentResponse.Response.Data) -> Unit) {
        this.onClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_doc_pasien, parent, false)
        return ListDokumenAdapater.ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val document = listDokumen[position]
        val url =
            "https://androidtest.farmagitechs.co.id/api/patient_document/serve_doc_thumbnail/${document.patientDocumentId}"
        val glideUrl = GlideUrl(
            url,
            LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer ${Preferences(holder.itemView.context).token}")
                .build()
        )
        Glide.with(holder.itemView.context)
            .load(glideUrl)
            .into(holder.ivDoc)
        holder.tvNamaDoc.text = document.title
        holder.tvTglDoc.text = convertLongToTime(document.createdTime)

        holder.itemView.setOnClickListener {
            onClick(document)
        }
    }

    override fun getItemCount(): Int {
        return listDokumen.size
    }

    //convert tgl
    @SuppressLint("SimpleDateFormat")
    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd MM yyyy HH:mm")
        return format.format(date)
    }
}