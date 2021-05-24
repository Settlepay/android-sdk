package net.settlepay.sdk.data.model

import com.google.gson.annotations.SerializedName

data class Result(

        @SerializedName("pay_url")
        val payUrl: String,

        @SerializedName("payer_auth")
        val payerAuth: String,

        @SerializedName("redirect_url")
        val redirectUrl: String?,

        @SerializedName("acs_url")
        val acsUrl: String?,

        @SerializedName("md")
        val md: Int?,

        @SerializedName("pareq")
        val pareq: String?
)