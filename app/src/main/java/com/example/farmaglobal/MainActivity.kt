package com.example.farmaglobal

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.farmaglobal.Adapter.ListDokumenAdapater
import com.example.farmaglobal.Adapter.ListPasienAdapter
import com.example.farmaglobal.Network.ApiClient
import com.example.farmaglobal.Network.DocumentResponse
import com.example.farmaglobal.Network.PasienResponse
import com.example.farmaglobal.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ApiClient.api.pasien(
            hashMapOf(
                "key" to "",
                "patient_id" to "",
                "patient_name" to ""
            ),
            "Bearer ${Preferences(this).token}"
        ).enqueue(object : Callback<PasienResponse>{
            override fun onResponse(
                call: Call<PasienResponse>,
                response: Response<PasienResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val adapter = ListPasienAdapter(it.response.data)
                        binding.rvPasien.adapter = adapter

                        adapter.onClickItem { pasien ->
                            binding.tvTitlePasien.text = pasien.patientName
                            binding.tvNoRm.text = pasien.patientId
                            binding.tvKelamin.text = pasien.gender
                            binding.tvAlamat.text = pasien.address
                            binding.tvTanggalLahir.text = convertLongToTime(pasien.dateBirth)

                            callDocument(pasien.patientId)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<PasienResponse>, t: Throwable) {
                Log.e("okhttp", t.message ?: t.localizedMessage)
            }

        })
    }

    private fun callDocument(id: String) {
        Log.e("okhttp", "kepanggil")
        ApiClient.api.document(
            hashMapOf(
                "patient_id" to id
            ),
            "Bearer ${Preferences(this).token}"
        ).enqueue(object : Callback<DocumentResponse>{
            override fun onResponse(
                call: Call<DocumentResponse>,
                response: Response<DocumentResponse>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        val adapter = ListDokumenAdapater(it.response.data)
                        binding.rvDokumen.adapter=adapter

                        adapter.onClickItem { document ->
                            val intent = Intent(this@MainActivity, Document::class.java)
                            intent.putExtra("id", document.patientDocumentId)
                            startActivity(intent)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<DocumentResponse>, t: Throwable) {
                Log.e("okhttp", t.message ?: t.localizedMessage)
            }

        })
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd MMMM yyyy")
        return format.format(date)
    }
}