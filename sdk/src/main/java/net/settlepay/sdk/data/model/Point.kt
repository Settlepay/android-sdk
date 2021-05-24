package net.settlepay.sdk.data.model

import com.google.gson.annotations.SerializedName

data class Point(
        @SerializedName("callback_url")
        val callbackUrl: String?,

        @SerializedName("success_url")
        val successUrl: String?,

        @SerializedName("fail_url")
        val failUrl: String?,

        @SerializedName("cancel_url")
        val cancelUrl: String?,

        @SerializedName("return_url")
        val returnUrl: String?
)