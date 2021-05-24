package net.settlepay.sdk.data.network.security

import android.util.Log
import net.settlepay.sdk.data.model.Authorization
import java.security.MessageDigest

internal object AuthEncryption {
    private val TAG = AuthEncryption::class.simpleName

    //    //Util Extensions
    private fun String.toMD5() = MessageDigest.getInstance("MD5").digest(this.toByteArray()).toHex()
    private fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }

    internal fun getAuth(authPoin: Int, apiToken: String, authKey: Long): Authorization{
        val hash = "$authPoin$apiToken$authKey".toMD5()
        Log.d(TAG, "authPoin $authPoin")
        Log.d(TAG, "apiToken $authKey")
        Log.d(TAG, "apiHash $hash")
        return Authorization(authPoin, authKey, hash)
    }
}
