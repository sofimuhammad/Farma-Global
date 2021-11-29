package com.example.farmaglobal.Network


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PasienResponse(
    @SerializedName("metaData")
    val metaData: MetaData,
    @SerializedName("response")
    val response: Response
) {
    @Keep
    data class MetaData(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: List<Any>
    )

    @Keep
    data class Response(
        @SerializedName("data")
        val `data`: ArrayList<Data>,
        @SerializedName("limit")
        val limit: Int,
        @SerializedName("page")
        val page: Int,
        @SerializedName("total")
        val total: Int
    ) {
        @Keep
        data class Data(
            @SerializedName("address")
            val address: String,
            @SerializedName("date_birth")
            val dateBirth: Long,
            @SerializedName("gender")
            val gender: String,
            @SerializedName("patient_id")
            val patientId: String,
            @SerializedName("patient_name")
            val patientName: String
        )
    }
}