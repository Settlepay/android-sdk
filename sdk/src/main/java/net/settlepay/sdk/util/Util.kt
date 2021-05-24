package net.settlepay.sdk.util

import java.net.URLEncoder

internal fun getPostData(mdValue: String, paReqValue: String, termUrlValue: String): ByteArray{
    val postData = "MD=${URLEncoder.encode(mdValue, "UTF-8")}&PaReq=${URLEncoder.encode(paReqValue, "UTF-8")}&TermUrl=${URLEncoder.encode(termUrlValue, "UTF-8")}"
    return postData.toByteArray()
}