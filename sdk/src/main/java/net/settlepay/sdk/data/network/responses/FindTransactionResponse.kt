package net.settlepay.sdk.data.network.responses

import com.google.gson.annotations.SerializedName
import net.settlepay.sdk.data.model.Fields
import net.settlepay.sdk.data.model.FindAuthorization
import net.settlepay.sdk.data.model.Status

data class FindTransactionResponse(
    @SerializedName("error")
    val status: Status,

    @SerializedName("response")
    val response: FindResponse
){
    data class FindResponse(

        @SerializedName("account_id")
        val accountId: Int,

        @SerializedName("account_wallet_amount")
        val accountWalletAmount: Int?,

        @SerializedName("account_wallet_currency")
        val accountWalletCurrency: String,

        @SerializedName("amount")
        val amount: Int,

        @SerializedName("amount_currency")
        val amountCurrency: String?,

        @SerializedName("created_at")
        val createdAt: String,

        @SerializedName("description")
        val description: String?,

        @SerializedName("external_customer_id")
        val externalCustomerId: String?,

        @SerializedName("external_order_id")
        val externalOrderId: String?,

        @SerializedName("external_transaction_id")
        val externalTransactionId: String?,

        @SerializedName("id")
        val id: Int,

        @SerializedName("is_test")
        val isTest: Boolean,

        @SerializedName("point_id")
        val pointId: Int,

        @SerializedName("service_id")
        val serviceId: Int,

        @SerializedName("wallet_id")
        val walletId: Int,

        @SerializedName("status")
        val status: Int,

        @SerializedName("status_description")
        val statusDescription: String,

        @SerializedName("extra")
        val extra: Map<String, String>,

        @SerializedName("fields")
        val fields: Fields,

        @SerializedName("authorization")
        val authorization: FindAuthorization?,

        @SerializedName("failure_reason_code")
        val failure_reason_code: Int?,

        @SerializedName("failure_reason_message")
        val failure_reason_message: String?
    )
}