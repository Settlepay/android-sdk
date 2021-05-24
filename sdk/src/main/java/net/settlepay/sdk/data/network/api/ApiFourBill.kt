package net.settlepay.sdk.data.network.api

import net.settlepay.sdk.data.model.*
import net.settlepay.sdk.data.network.responses.*
import retrofit2.http.Body
import retrofit2.http.POST

internal interface ApiFourBill {

    //    WEB
    @POST("transaction/create")
    suspend fun createTransaction(@Body params: CreateTransaction): CreateTransactionResponse

    @POST("transaction/create")
    suspend fun createTransactionCardToCard(@Body params: CreateTransaction): CreateTransactionResponse

    @POST("transaction/create")
    suspend fun createTokenCard(@Body params: CreateTransaction): CreateTransactionResponse

    @POST("transaction/find")
    suspend fun findTransaction(@Body params: FindTransaction): FindTransactionResponse

    @POST("/transaction/cancel")
    suspend fun cancelTransaction(@Body params: CancelTransaction): CancelTransactionResponse

    //    HOST
    @POST("transaction/pay")
    suspend fun createHostToHostTransaction(@Body params: PayTransaction): PayTransactionResponse

    @POST("transaction/update")
    suspend fun createUpdateLookupTransaction(@Body params: UpdateTransaction): UpdateTransactionResponse

    @POST("transaction/update")
    suspend fun createUpdateOtpTransaction(@Body params: UpdateTransaction): UpdateTransactionResponse

    @POST("transaction/update")
    suspend fun createUpdate3DSTransaction(@Body params: UpdateTransaction): UpdateTransactionResponse
}