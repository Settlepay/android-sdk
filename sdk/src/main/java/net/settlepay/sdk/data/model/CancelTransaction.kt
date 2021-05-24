package net.settlepay.sdk.data.model

import com.google.gson.annotations.SerializedName

internal data class CancelTransaction(
    @SerializedName("auth")
    val auth: Authorization,

    @SerializedName("id")
    val id: Int
)