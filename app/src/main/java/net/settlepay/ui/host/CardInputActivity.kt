package net.settlepay.ui.host


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.settlepay.R
import net.settlepay.core.TransactionManager
import net.settlepay.core.TransactionManager.SERVICE_ID_HOST_TO_HOST
import net.settlepay.core.TransactionManager.TEMP_URL_FOR_CALLBACK
import net.settlepay.sdk.data.model.CardInputData
import net.settlepay.sdk.data.model.Fields
import net.settlepay.sdk.data.model.Result
import net.settlepay.sdk.data.network.responses.PayTransactionResponse.PayResponse
import net.settlepay.sdk.util.*
import net.settlepay.sdk.view.CardInputView
import net.settlepay.ui.web.WebActivity
import net.settlepay.util.getRandomNumber
import net.settlepay.util.showInputDialog
import net.settlepay.util.showProgressDialog
import net.settlepay.util.showToast


class CardInputActivity : AppCompatActivity(), CardInputView.CardInputListener {

    private val TAG = CardInputActivity::class.simpleName

    private val appSDK = TransactionManager.sdk

    private lateinit var idTransaction: String
    private lateinit var cardInputView: CardInputView

    companion object {
        const val PAY_SUMM          = "paySumm"
        const val ID_TRANSACTION    = "idTransaction"
        const val CARD_ACTIVITY     = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_input)

        generateData()
    }

    override fun onInputCompleted(cardInputData: CardInputData) {
        createHostTransaction(cardInputData)
    }

    private fun generateData(){

        idTransaction = getRandomNumber()

        cardInputView = findViewById(R.id.cardInputView)
        cardInputView.setTransactionLogo(R.drawable.settlepay_logo)
        cardInputView.setTransactionTitle(getString(R.string.number_transaction_text))
        cardInputView.setTransactionNumber("#$idTransaction")
        cardInputView.setTransactionSumm("${intent.getStringExtra(PAY_SUMM)}.00")
        cardInputView.setCardInputListener(this)
    }

    private fun createHostTransaction(cardInputData: CardInputData){

        val progress = showProgressDialog(this, "Loading...")

        CoroutineScope(Dispatchers.Main).launch {
            try {

                //Format card data
                val summ = cardInputData.summ.toFloat().toInt() *100
                val year = "20${cardInputData.year}"

                val response = appSDK.createHostToHostTransaction(SERVICE_ID_HOST_TO_HOST,
                    RU, idTransaction,
                    "8.8.8.8", summ,
                    "UAH", null,
                    null, "Sample h2h payment",
                    Fields.createCardData(cardInputData.number, year, cardInputData.month, cardInputData.cvv),
                    null, null)

                when (response.status.code) {
                    SUCCES -> {

                        Log.d(TAG, "OK Transaction ${response.response.id}")

                        checkAuth(response.response)
                        progress.dismiss()
                    }

                    else -> {
                        Log.d(TAG, "${response.status.code}: ${response.status.title}")
                        progress.dismiss()
                        showToast("ERROR ${response.status.code}: ${response.status.title}")
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
                Log.d(TAG, "ERROR createTransaction $e")
                progress.dismiss()
                showToast("ERROR ${e.message}")
            }
        }
    }

    private fun checkAuth(response: PayResponse){

        returnTransactionID(response.id.toString())

        when(response.result!!.payerAuth){

            NONE -> {
                Log.d(TAG, "Authorisation NONE")
                showToast("Transaction stutus ${response.status}")
                finish()
            }
            LOOKUP -> {
                showInputDialog(this, getString(R.string.dialog_enter_lookup_code_text)) { code ->
                    createUpdateLookupTransaction(response.id, code.toString())
                }
            }
            OTP -> {
                showInputDialog(this, getString(R.string.dialog_enter_otp_code_text)) { code ->
                    createUpdateOtpTransaction(response.id, code.toString())
                }
            }
            REDIRECT -> redirectTransaction(response.result!!.redirectUrl!!)
            UNKNOWN -> Log.d(TAG, "Authorisation UNKNOWN")
            THREEDS -> create3DSTransaction(response.result!!)
        }
    }

    private fun createUpdateLookupTransaction(id: Int, code: String){
//        4000000000000077

        Log.d(TAG, "Authorisation LOOKUP")

        val progress = showProgressDialog(this, "Loading...")

        CoroutineScope(Dispatchers.Main).launch {
            try {

                val response = appSDK.createUpdateLookupTransaction(id, code)

                when (response.status.code) {
                    SUCCES -> {

                        Log.d(TAG, "OK Transaction ${response.response.status}")
                        showToast("Transaction stutus ${response.response.status}")
                        progress.dismiss()
                        finish()
                    }

                    else -> {
                        Log.d(TAG, "${response.status.code}: ${response.status.title}")
                        progress.dismiss()
                        showToast("ERROR ${response.status.code}: ${response.status.title}")
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
                Log.d(TAG, "ERROR createTransaction $e")
                progress.dismiss()
                showToast("ERROR ${e.message}")
            }
        }
    }

    private fun createUpdateOtpTransaction(id: Int, code: String){
//        4000000000000051

        Log.d(TAG, "Authorisation OTP")

        val progress = showProgressDialog(this, "Loading...")

        CoroutineScope(Dispatchers.Main).launch {
            try {

                val response = appSDK.createUpdateOtpTransaction(id, code)

                when (response.status.code) {
                    SUCCES -> {

                        Log.d(TAG, "OK Transaction ${response.response.status}")
                        showToast("Transaction stutus ${response.response.status}")
                        progress.dismiss()
                        finish()
                    }

                    else -> {
                        Log.d(TAG, "${response.status.code}: ${response.status.title}")
                        progress.dismiss()
                        showToast("ERROR ${response.status.code}: ${response.status.title}")
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
                Log.d(TAG, "ERROR createTransaction $e")
                progress.dismiss()
                showToast("ERROR ${e.message}")
            }
        }
    }

    private fun redirectTransaction(url: String){
//        4000000000000069

        Log.d(TAG, "Authorisation REDIRECT $url")

        startWebActivity(url)
        finish()
    }


    private fun create3DSTransaction(result: Result){
//        4000000000000036

        Log.d(TAG, "Authorisation THREEDS")

        val webUrl = result.acsUrl
        val mdValue = result.md.toString()
        val paReqValue = result.pareq

        startWebWithPostActivity(webUrl!!, appSDK.createPostData(mdValue, paReqValue!!, TEMP_URL_FOR_CALLBACK))
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cardInputView.onActivityResult(requestCode, resultCode, data)
    }

    private fun returnTransactionID(id: String){
        val intent = Intent()
        intent.putExtra(ID_TRANSACTION, id)
        setResult(Activity.RESULT_OK, intent)
    }

    private fun startWebActivity(payUrl: String){
        val intent = Intent(this, WebActivity::class.java)
        intent.putExtra(WebActivity.WEB_URL, payUrl)
        startActivity(intent)
    }

    private fun startWebWithPostActivity(webUrl: String, postData: ByteArray){
        val intent = Intent(this, WebActivity::class.java)
        intent.putExtra(WebActivity.WEB_URL, webUrl)
        intent.putExtra(WebActivity.POST_DATA, postData)
        startActivity(intent)
    }
}