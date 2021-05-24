package net.settlepay.sdk.core

import net.settlepay.sdk.data.model.*
import net.settlepay.sdk.data.network.responses.*
import net.settlepay.sdk.data.network.security.AuthEncryption
import net.settlepay.sdk.manager.HostPaymentsManager
import net.settlepay.sdk.manager.WebPaymentsManager
import net.settlepay.sdk.util.getPostData

class SettlepaySdk(authPoin: Int, apiToken: String, authKey: Long, accountId: Int, walletId: Int) {

    private val TAG = SettlepaySdk::class.simpleName

    private val AUTH_POINT: Int
    private val API_TOKEN: String
    private val AUTH_KEY: Long
    private val ACCOUNT_ID: Int
    private val WALLET_ID: Int
    private val webPaymentsManager = WebPaymentsManager()
    private val hostPaymentsManager = HostPaymentsManager()

    init {
        AUTH_POINT = authPoin
        API_TOKEN  = apiToken
        AUTH_KEY   = authKey
        ACCOUNT_ID = accountId
        WALLET_ID  = walletId
    }

    private fun sdkAuth(): Authorization{
        return AuthEncryption.getAuth(AUTH_POINT, API_TOKEN, AUTH_KEY)
    }

    suspend fun createWebTransaction(serviceId: Int, locale: String, externalTransactionId: String,
                                     customerIpAddress: String, amount: Int, amountCurrency: String,
                                     externalOrderId: String?, externalCustomerId: String?,
                                     description: String?, fields: Fields?,
                                     point: Point?, extra: Map<String, String>?
    ): CreateTransactionResponse{
        return webPaymentsManager.createTransaction(CreateTransaction(sdkAuth(), serviceId,
            ACCOUNT_ID, WALLET_ID, locale, externalTransactionId, customerIpAddress,
            amount, amountCurrency, externalOrderId, externalCustomerId, description,
            fields, point, extra ))
    }

    suspend fun createTransactionCardToCard(serviceId: Int, locale: String, externalTransactionId: String,
                                            customerIpAddress: String, amount: Int, amountCurrency: String,
                                            externalOrderId: String?, externalCustomerId: String?,
                                            description: String?, fields: Fields?,
                                            point: Point?, extra: Map<String, String>?
    ): CreateTransactionResponse{
        return webPaymentsManager.createTransactionCardToCard(CreateTransaction(sdkAuth(), serviceId,
            ACCOUNT_ID, WALLET_ID, locale, externalTransactionId, customerIpAddress,
            amount, amountCurrency, externalOrderId, externalCustomerId, description,
            fields, point, extra ))
    }

    suspend fun createTokenCard(serviceId: Int, locale: String, externalTransactionId: String,
                                customerIpAddress: String,
                                externalOrderId: String?, externalCustomerId: String?,
                                description: String?, fields: Fields?,
                                point: Point?, extra: Map<String, String>?
    ): CreateTransactionResponse{
        return webPaymentsManager.createTokenCard(CreateTransaction(sdkAuth(), serviceId,
            ACCOUNT_ID, WALLET_ID, locale, externalTransactionId, customerIpAddress,
            null, null, externalOrderId, externalCustomerId, description,
            fields, point, extra ))
    }

    suspend fun cancelTransaction(id: Int): CancelTransactionResponse {
        return webPaymentsManager.cancelTransaction(CancelTransaction(sdkAuth(), id))
    }

    suspend fun checkTransactionStatus(id: Int?, externalTransactionId: String?): FindTransactionResponse {
        return webPaymentsManager.findTransaction(FindTransaction(sdkAuth(), id, externalTransactionId))
    }

    suspend fun createHostToHostTransaction(serviceId: Int, locale: String, externalTransactionId: String,
                                            customerIpAddress: String, amount: Int,
                                            amountCurrency: String,
                                            externalOrderId: String?, externalCustomerId: String?,
                                            description: String?, fields: Fields?,
                                            point: Point?, extra: Map<String, String>?
    ): PayTransactionResponse {
        return hostPaymentsManager.createHostToHostTransaction(PayTransaction(sdkAuth(), serviceId,
            ACCOUNT_ID, WALLET_ID, locale, externalTransactionId, customerIpAddress, amount,
            amountCurrency, externalOrderId, externalCustomerId, description,
            fields, point, extra ))
    }

    suspend fun createUpdateLookupTransaction(id: Int, code: String
    ): UpdateTransactionResponse {
        return hostPaymentsManager.createUpdateLookupTransaction(UpdateTransaction(sdkAuth(), id, Data.addLookUp(code)))
    }

    suspend fun createUpdateOtpTransaction(id: Int, code: String
    ): UpdateTransactionResponse {
        return hostPaymentsManager.createUpdateOtpTransaction(UpdateTransaction(sdkAuth(), id, Data.addOtp(code)))
    }

    suspend fun createUpdate3DSTransaction(id: Int, md: String, paRes: String
    ): UpdateTransactionResponse {
        return hostPaymentsManager.createUpdate3DSTransaction(UpdateTransaction(sdkAuth(), id, Data.add3DS(md, paRes)))
    }

    fun createPostData(mdValue: String, paReqValue: String, termUrlValue: String): ByteArray{
        return getPostData(mdValue, paReqValue, termUrlValue)
    }
}