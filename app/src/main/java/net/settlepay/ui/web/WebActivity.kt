package net.settlepay.ui.web

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import net.settlepay.R
import kotlinx.android.synthetic.main.activity_web.*


class WebActivity : AppCompatActivity() {

    companion object {
        const val WEB_URL = "webUrl"
        const val POST_DATA = "postData"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        val webUrl   = intent.getStringExtra(WEB_URL)
        val postData = intent.getByteArrayExtra(POST_DATA)

        initWebView()

        if (postData != null){
            loadWebWithPostData(webUrl!!, postData)
        }else{
            loadWeb(webUrl!!)
        }
    }

    private fun initWebView() {

        webView.webViewClient = WebViewClient()
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
    }

    private fun loadWeb(webUrl: String){
        webView.loadUrl(webUrl)
    }

    private fun loadWebWithPostData(webUrl: String, postData: ByteArray){
        webView.postUrl(webUrl, postData)
    }
}