package net.settlepay.sdk.data.model

import com.google.gson.annotations.SerializedName

data class Fields(
    @SerializedName("card_token")
    val cardToken: String?,

    @SerializedName("recipient_card_number")
    val recipientCardNumber: String?,

    @SerializedName("issue_card_token")
    val issueCardToken: String?,

    @SerializedName("card_hash")
    val cardHash: String?,

    @SerializedName("card_number")
    val cardNumber: String?,

    @SerializedName("expire_year")
    val expireYear: String?,

    @SerializedName("expire_month")
    val expireMonth: String?,

    @SerializedName("cvv")
    val cvv: String?

){
    companion object{
        fun addRecipientCardNumber(
            recipientCardNumber: String
        ): Fields {
            return Fields(null, recipientCardNumber, null, null,null,null, null, null)
        }

        fun createTokenCard(): Fields {
            return Fields(null, null, true.toString(), null, null, null, null, null)
        }

        fun createCardData(
            cardNumber: String,
            expireYear: String,
            expireMonth: String,
            cvv: String
        ): Fields {
            return Fields(null, null, null, null, cardNumber, expireYear, expireMonth, cvv)
        }
    }
}