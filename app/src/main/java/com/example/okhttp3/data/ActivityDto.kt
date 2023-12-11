package com.example.okhttp3.data


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ActivityDto(
    @SerializedName("activity")
    val activity: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("participants")
    val participants: Int,
    @SerializedName("price")
    val price: Double,
    @SerializedName("link")
    val link: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("accessibility")
    val accessibility: Double
)