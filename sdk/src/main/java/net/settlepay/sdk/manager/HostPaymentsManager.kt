package net.settlepay.sdk.manager

import net.settlepay.sdk.data.model.PayTransaction
import net.settlepay.sdk.data.model.UpdateTransaction
import net.settlepay.sdk.data.network.api.ApiFourBill
import net.settlepay.sdk.data.network.api.RetrofitBuilder

internal class HostPaymentsManager {

    private var apiHelper: ApiFourBill = RetrofitBuilder.apiService

    suspend fun createHostToHostTransaction(payTransaction: PayTransaction) = apiHelper.createHostToHostTransaction(payTransaction)
    suspend fun createUpdateLookupTransaction(updateTransaction: UpdateTransaction) = apiHelper.createUpdateLookupTransaction(updateTransaction)
    suspend fun createUpdateOtpTransaction(updateTransaction: UpdateTransaction) = apiHelper.createUpdateOtpTransaction(updateTransaction)
    suspend fun createUpdate3DSTransaction(updateTransaction: UpdateTransaction) = apiHelper.createUpdate3DSTransaction(updateTransaction)
}