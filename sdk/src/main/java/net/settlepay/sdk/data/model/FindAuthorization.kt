package net.settlepay.sdk.data.model

import com.google.gson.annotations.SerializedName

data class FindAuthorization(

    @SerializedName("acs_url")
    val acsUrl: String,

    @SerializedName("md")
    val md: Int,

    @SerializedName("pareq")
    val pareq: String,

    @SerializedName("payer_auth")
    val payerAuth: String
)