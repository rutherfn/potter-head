package com.nicholas.rutherford.potter.head.model.network

import com.google.gson.annotations.SerializedName

data class WandResponse(
    @SerializedName("wood")
    val wood: String?,
    @SerializedName("core")
    val core: String?,
    @SerializedName("length")
    val length: Double?
)