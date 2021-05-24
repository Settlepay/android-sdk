package net.settlepay.sdk.data.network.responses

import com.google.gson.annotations.SerializedName
import net.settlepay.sdk.data.model.Result
import net.settlepay.sdk.data.model.Status

data class PayTransactionResponse(
    @SerializedName("error")
    val status: Status,

    @SerializedName("response")
    val response: PayResponse
){
    class PayResponse(
        @SerializedName("id")
        val id: Int,

        @SerializedName("is_test")
        val isTest: Boolean,

        @SerializedName("status")
        val status: Int,

        @SerializedName("status_description")
        val statusDescription: String,

        @SerializedName("failure_reason_code")
        val failureReasonCode: Int?,

        @SerializedName("failure_reason_message")
        val failureReasonMessage: String?,

        @SerializedName("result")
        val result: Result?
    )
}