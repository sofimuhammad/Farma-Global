package com.example.farmaglobal.Network

import com.example.farmaglobal.Network.ApiClient.api
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Streaming

interface ApiServices {
    //step 2. copy link API Login
    @POST("api/auth/generate_token")
    fun login(@Body body: HashMap<String, String>): Call<LoginResponse>

    @POST("api/patient/list/1")
    fun pasien(@Body body: HashMap<String, String>, @Header("authorization") token: String): Call<PasienResponse>

    @POST("api/patient_document/list_doc")
    fun document(@Body body: HashMap<String, String>, @Header("authorization")token: String): Call<DocumentResponse>

    @POST("api/patient_document/serve_doc")
    @Streaming
    fun getPdf(@Body body: HashMap<String, Int>, @Header("authorization") token: String): Call<ResponseBody>
}