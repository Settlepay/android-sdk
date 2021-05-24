package net.settlepay.sdk.data.network.responses

import com.google.gson.annotations.SerializedName
import net.settlepay.sdk.data.model.Result
import net.settlepay.sdk.data.model.Status

data class CreateTransactionResponse(

    @SerializedName("error")
    val status: Status,

    @SerializedName("response")
    val response: CreateResponse
){
    data class CreateResponse(

        @SerializedName("amount")
        val amount: Int,

        @SerializedName("amount_currency")
        val amountCurrency: String,

        @SerializedName("external_customer_id")
        val externalCustomerId: String,

        @SerializedName("external_transaction_id")
        val externalTransactionId: String,

        @SerializedName("external_order_id")
        val externalOrderId: String,

        @SerializedName("id")
        val id: Int,

        @SerializedName("is_test")
        val isTest: Boolean,

        @SerializedName("account_id")
        val accountId: Int,

        @SerializedName("point_id")
        val pointId: Int,

        @SerializedName("service_id")
        val serviceId: Int,

        @SerializedName("wallet_id")
        val walletId: Int,

        @SerializedName("result")
        val result: Result,

        @SerializedName("status")
        val status: Int,

        @SerializedName("status_description")
        val statusDescription: String?,

        @SerializedName("failure_reason_code")
        val failureReasonCode: String?,

        @SerializedName("failure_reason_message")
        val failureReasonMessage: String?
    )
}