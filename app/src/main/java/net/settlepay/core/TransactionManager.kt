package net.settlepay.core

import net.settlepay.sdk.core.SettlepaySdk

object TransactionManager {

    //TEST DATA
    private const val AUTH_POINT: Int = //Enter your data
    private const val API_TOKEN: String  = //Enter your data
    private val AUTH_KEY = System.currentTimeMillis() / 1000L

    private const val ACCOUNT_ID: Int = //Enter your data
    private const val WALLET_ID: Int  = //Enter your data
    private const val SERVICE_ID_WEB_WITH_TOKEN: Int  = //Enter your data
    const val SERVICE_ID_WEB: Int  = //Enter your data
    const val SERVICE_ID_TOKEN: Int  = //Enter your data
    const val SERVICE_ID_HOST_TO_HOST: Int  = //Enter your data


    //Test temp url for callback
    const val TEMP_URL_FOR_CALLBACK:String  = //Enter your data

    val sdk: SettlepaySdk = SettlepaySdk(AUTH_POINT, API_TOKEN, AUTH_KEY, ACCOUNT_ID, WALLET_ID)
}