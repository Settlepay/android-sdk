package net.settlepay.sdk.data.model

import com.google.gson.annotations.SerializedName

internal data class Data(

    @SerializedName("PaRes")
    val paRes: String?,

    @SerializedName("MD")
    val md: String?,

    @SerializedName("lookup_code")
    val lookupCode: String?,

    @SerializedName("otp_code")
    val otpCode: String?
){
    companion object{

        fun addLookUp(code: String
        ): Data {
            return Data(null, null, code, null)
        }

        fun addOtp(code: String
        ): Data {
            return Data(null, null, null, code)
        }

        fun add3DS(md: String, paRes: String
        ): Data {
            return Data(paRes, md, null, null)
        }
    }
}