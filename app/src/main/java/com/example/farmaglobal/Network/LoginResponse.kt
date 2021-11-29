package com.example.farmaglobal.Network


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

//copy respon Login
@Keep
data class LoginResponse(
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
        val message: List<String>
    )

    @Keep
    data class Response(
        @SerializedName("token")
        val token: String
    )
}