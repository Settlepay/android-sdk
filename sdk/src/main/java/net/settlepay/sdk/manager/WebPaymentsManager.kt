package net.settlepay.sdk.manager

import net.settlepay.sdk.data.model.CancelTransaction
import net.settlepay.sdk.data.model.CreateTransaction
import net.settlepay.sdk.data.model.FindTransaction
import net.settlepay.sdk.data.network.api.ApiFourBill
import net.settlepay.sdk.data.network.api.RetrofitBuilder

internal class WebPaymentsManager {

    private var apiHelper: ApiFourBill = RetrofitBuilder.apiService

    suspend fun createTransaction(createTransaction: CreateTransaction) = apiHelper.createTransaction(createTransaction)
    suspend fun createTransactionCardToCard(createTransaction: CreateTransaction) = apiHelper.createTransactionCardToCard(createTransaction)
    suspend fun createTokenCard(createTransaction: CreateTransaction) = apiHelper.createTokenCard(createTransaction)
    suspend fun findTransaction(findTransaction: FindTransaction) = apiHelper.findTransaction(findTransaction)
    suspend fun cancelTransaction(cancelTransaction: CancelTransaction) = apiHelper.cancelTransaction(cancelTransaction)
}
