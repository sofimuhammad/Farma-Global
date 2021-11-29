package com.example.farmaglobal.Network


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class DocumentResponse(
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
        @SerializedName("total")
        val total: Int
    ) {
        @Keep
        data class Data(
            @SerializedName("created_time")
            val createdTime: Long,
            @SerializedName("deleted")
            val deleted: Int,
            @SerializedName("document_code")
            val documentCode: String,
            @SerializedName("filename")
            val filename: String,
            @SerializedName("modified_time")
            val modifiedTime: Long,
            @SerializedName("note")
            val note: String,
            @SerializedName("patient_document_id")
            val patientDocumentId: Int,
            @SerializedName("patient_id")
            val patientId: String,
            @SerializedName("thumbnail")
            val thumbnail: String,
            @SerializedName("title")
            val title: String,
            @SerializedName("user")
            val user: User,
            @SerializedName("user_id")
            val userId: Int
        ) {
            @Keep
            data class User(
                @SerializedName("active")
                val active: String,
                @SerializedName("fullname")
                val fullname: String,
                @SerializedName("group_id")
                val groupId: Int,
                @SerializedName("group_name")
                val groupName: String,
                @SerializedName("user_id")
                val userId: Int,
                @SerializedName("username")
                val username: String
            )
        }
    }
}