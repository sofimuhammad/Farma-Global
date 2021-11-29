package com.example.farmaglobal

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.farmaglobal.Network.ApiClient
import com.example.farmaglobal.databinding.ActivityDocumentBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


class Document : AppCompatActivity() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityDocumentBinding.inflate(layoutInflater)
    }

    private val id by lazy {
        intent.getIntExtra("id", 2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ApiClient.api.getPdf(
            hashMapOf(
                "patient_document_id" to id
            ), "Bearer ${Preferences(this).token}"
        ).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val downloaded = response.body()

                    downloaded?.let {
                        val isDownloaded = downloadImage(it)
                        Log.e("okhttp", "isDownloaded: $isDownloaded")
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

        })
    }

    private fun downloadImage(res: ResponseBody): Boolean {
        val appDirectoryName = "pdf_document"
        val filename = "pdf_${id}.jpg"
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename)
        try {
            var inputStream: InputStream? = null
            var fos: FileOutputStream? = null

            try {
                inputStream = res.byteStream()
                fos = FileOutputStream(file)
                var c: Int

                while (inputStream.read().also { c = it } != -1) {
                    fos.write(c)
                }
            } catch (e: Exception) {
                Log.e("okhttp", e.message ?: e.localizedMessage)
                return false
            } finally {
                inputStream?.close()
                fos?.close()
            }

            val width: Int
            val height: Int
            val image = binding.webView
            Log.e("okhttp", "${file.path} absolute path ${file.absolutePath}")
            val bMap = BitmapFactory.decodeFile(file.path)
            width = 2 * bMap.width
            height = 6 * bMap.height
            val bMap2 = Bitmap.createScaledBitmap(bMap, width, height, false)
            image.setImageBitmap(bMap2)

            return true
        } catch (e: Exception) {
            Log.e("okhttp", e.message ?: e.localizedMessage)
            return false
        }
    }
}