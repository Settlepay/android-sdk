package net.settlepay.sdk.data.model

import com.google.gson.annotations.SerializedName

internal data class UpdateTransaction(
    @SerializedName("auth")
    val auth: Authorization,

    @SerializedName("id")
    val id: Int,

    @SerializedName("data")
    val data: Data
)