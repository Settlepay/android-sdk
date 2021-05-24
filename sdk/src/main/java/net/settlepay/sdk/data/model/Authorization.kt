package net.settlepay.sdk.data.model

import com.google.gson.annotations.SerializedName

internal data class Authorization(
    @SerializedName("point")
    val point: Int,

    @SerializedName("key")
    val key: Long,

    @SerializedName("hash")
    val hash: String = "uk"
)