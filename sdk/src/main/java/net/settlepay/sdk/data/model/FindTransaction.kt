package net.settlepay.sdk.data.model

import com.google.gson.annotations.SerializedName

internal data class FindTransaction(
    @SerializedName("auth")
        val auth: Authorization,

    @SerializedName("id")
        val id: Int?,

    @SerializedName("external_transaction_id")
        val externalTransactionId: String?
)