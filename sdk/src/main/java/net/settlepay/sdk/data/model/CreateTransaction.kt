package net.settlepay.sdk.data.model

import com.google.gson.annotations.SerializedName

internal data class CreateTransaction(

    @SerializedName("auth")
    val auth: Authorization,

    @SerializedName("service_id")
    val serviceId: Int,

    @SerializedName("account_id")
    val accountId: Int,

    @SerializedName("wallet_id")
    val walletId: Int,

    @SerializedName("locale")
    val locale: String,

    @SerializedName("external_transaction_id")
    val externalTransactionId: String,

    @SerializedName("customer_ip_address")
    val customerIpAddress: String,

    @SerializedName("amount")
    val amount: Int?,

    @SerializedName("amount_currency")
    val amountCurrency: String?,

    @SerializedName("external_order_id")
    val externalOrderId: String?,

    @SerializedName("external_customer_id")
    val externalCustomerId: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("fields")
    val fields: Fields?,

    @SerializedName("point")
    val point: Point?,

    @SerializedName("extra")
    val extra: Map<String, String>?
)
