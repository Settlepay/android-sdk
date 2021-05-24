package net.settlepay.sdk.data.network.responses

import com.google.gson.annotations.SerializedName
import net.settlepay.sdk.data.model.Status

data class CancelTransactionResponse(

    @SerializedName("error")
    val status: Status,

    @SerializedName("response")
    val response: CancelResponse
){
    data class CancelResponse(

        @SerializedName("id")
        val id: Int,

        @SerializedName("is_test")
        val isTest: Boolean,

        @SerializedName("status")
        val status: Int
    )
}