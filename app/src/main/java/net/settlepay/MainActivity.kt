package net.settlepay

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import net.settlepay.core.TransactionManager
import net.settlepay.core.TransactionManager.SERVICE_ID_TOKEN
import net.settlepay.core.TransactionManager.SERVICE_ID_WEB
import net.settlepay.sdk.data.model.Fields
import net.settlepay.sdk.util.RU
import net.settlepay.sdk.util.SUCCES
import net.settlepay.ui.host.CardInputActivity
import net.settlepay.ui.host.CardInputActivity.Companion.CARD_ACTIVITY
import net.settlepay.ui.host.CardInputActivity.Companion.ID_TRANSACTION
import net.settlepay.ui.host.CardInputActivity.Companion.PAY_SUMM
import net.settlepay.ui.web.WebActivity
import net.settlepay.ui.web.WebActivity.Companion.WEB_URL
import net.settlepay.util.*


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.simpleName

    private val appSDK = TransactionManager.sdk

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initWebTransaction()

    }

    private fun initWebTransaction() {

        webTransactionButton.setOnClickListener {
            showInputDialog(this, getString(R.string.dialog_transaction_summ_text)) { summ ->
                clearText()
                createTransaction(summ.toInt()*100)
            }
        }

        webTransactionWithTokenButton.setOnClickListener {
            showInputDialog(this, getString(R.string.dialog_transaction_summ_text)) { summ ->
                clearText()
                createTransactionWithToken(summ.toInt()*100)
            }
        }

        webTransactionToCardButton.setOnClickListener {
            showInputCardNumberDialog(this, getString(R.string.dialog_card_number_text)) { curdNumber ->
                clearText()
                createCardToCardTransaction(curdNumber)
            }
        }

        cancelTransactionButton.setOnClickListener {
            showInputDialog(this, getString(R.string.dialog_transaction_number_text)) { transactionNumber ->
                clearText()
                cancelTransaction(transactionNumber.toInt())
            }
        }

        webTransactionStatusButton.setOnClickListener {
            showInputDialog(this, getString(R.string.dialog_transaction_number_text)) { transactionNumber ->
                clearText()
                checkTransactionStatus(transactionNumber.toInt(), null)
            }
        }

        tokenCardButton.setOnClickListener {
            clearText()
            createCardToken()
        }

        hostToHostButton.setOnClickListener {
            clearText()
            showInputDialog(this, getString(R.string.dialog_transaction_summ_text)) { summ ->
                startCardInputActivity(summ)
            }
        }

        hostTransactionStatusButton.setOnClickListener {
            clearText()
            showDoubleInputDialog(this, getString(R.string.dialog_transaction_status_text)){ transition ->
                createUpdate3DSTransaction(transition.id.toInt(), transition.id, transition.paRes)
            }

        }
    }

    private fun createTransaction(summ: Int) {

        val progress = showProgressDialog(this, "Loading...")

        CoroutineScope(Main).launch {
            try {
                val response = appSDK.createWebTransaction(SERVICE_ID_WEB,
                    RU, getRandomNumber(),
                    "8.8.8.8", summ,
                    "UAH", null,
                    null, null,
                    null, null, null)

                when (response.status.code) {
                    SUCCES -> {

                        Log.d(TAG, "OK ${response.response.result.payUrl}")

                        startWebActivity(response.response.result.payUrl)
                        progress.dismiss()

                        idTransactionTextView.text = "${getString(R.string.last_transaction_id_text)} ${response.response.id}"
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

    private fun createTransactionWithToken(summ: Int) {

        val progress = showProgressDialog(this, "Loading...")

        CoroutineScope(Main).launch {
            try {
                val response = appSDK.createWebTransaction(SERVICE_ID_WEB,
                    RU, getRandomNumber(),
                    "8.8.8.8", summ,
                    "UAH", null,
                    null, null,
                    Fields.createTokenCard(), null, null)

                when (response.status.code) {
                    SUCCES -> {

                        Log.d(TAG, "OK ${response.response.result.payUrl}")

                        startWebActivity(response.response.result.payUrl)
                        progress.dismiss()

                        idTransactionTextView.text = "${getString(R.string.last_transaction_id_text)} ${response.response.id}"
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

    private fun createCardToCardTransaction(cardNumber: String) {

        val progress = showProgressDialog(this, "Loading...")

        CoroutineScope(Main).launch {
            try {
                val response = appSDK.createTransactionCardToCard(SERVICE_ID_WEB,
                    RU, getRandomNumber(),
                    "8.8.8.8", 500,
                    "UAH", null,
                    null, null,
                    Fields.addRecipientCardNumber(cardNumber),
                    null, null)

                Log.d(TAG, "createTransaction $response")

                when (response.status.code) {

                    SUCCES -> {
                        Log.d(TAG, "OK ${response.response.result.payUrl}")

                        startWebActivity(response.response.result.payUrl)
                        progress.dismiss()

                        idTransactionTextView.text = "${getString(R.string.last_transaction_id_text)} ${response.response.id}"
                    }

                    else -> {
                        Log.d(TAG, "${response.status.code}: ${response.status.title}")
                        progress.dismiss()
                        showToast("ERROR ${response.status.code}: ${response.status.title}")
                    }
                }

            }catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG, "ERROR createTransaction $e")
                progress.dismiss()
                showToast("ERROR ${e.message}")
            }
        }
    }

    private fun cancelTransaction(id: Int) {

        val progress = showProgressDialog(this, "Loading...")

        CoroutineScope(Main).launch {
            try {
                val response = appSDK.cancelTransaction(id)

                Log.d(TAG, "createTransaction $response")

                when (response.status.code) {

                    SUCCES -> {
                        Log.d(TAG, "OK ${response.response.status}")

                        progress.dismiss()
                        idTransactionTextView.text = "${getString(R.string.last_transaction_status_text)} ${response.response.status}"
                    }

                    else -> {
                        Log.d(TAG, "${response.status.code}: ${response.status.title}")
                        progress.dismiss()
                        showToast("ERROR ${response.status.code}: ${response.status.title}")
                    }
                }

            }catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG, "ERROR createTransaction $e")
                progress.dismiss()
                showToast("ERROR ${e.message}")
            }
        }
    }

    private fun checkTransactionStatus(id: Int?, externalTransactionId: String?) {

        val progress = showProgressDialog(this, "Loading...")

        CoroutineScope(Main).launch {
            try {
                val response = appSDK.checkTransactionStatus(id, externalTransactionId)

                Log.d(TAG, "createTransaction $response")

                when (response.status.code) {

                    SUCCES -> {
                        Log.d(TAG, "OK ${response.response.status}")

                        progress.dismiss()
                        idTransactionTextView.text = "${getString(R.string.last_transaction_status_text)} ${response.response.status}"
                        cardNumberTextView.text = response.response.fields.cardNumber
                        cardTokenTextView.text = response.response.fields.cardToken

                        Log.d(TAG, "OK ${response.response.fields.cardNumber}")
                        Log.d(TAG, "OK ${response.response.fields.cardToken}")
                    }

                    else -> {
                        Log.d(TAG, "${response.status.code}: ${response.status.title}")
                        progress.dismiss()
                        showToast("ERROR ${response.status.code}: ${response.status.title}")
                    }
                }

            }catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG, "ERROR createTransaction $e")
                progress.dismiss()
                showToast("ERROR ${e.message}")
            }
        }
    }

    private fun createCardToken(){

        val progress = showProgressDialog(this, "Loading...")

        CoroutineScope(Main).launch {
            try {
                val response = appSDK.createTokenCard(SERVICE_ID_TOKEN,
                    RU, getRandomNumber(),
                    "8.8.8.8",null,
                    null, null,
                    null, null, null)

                when (response.status.code) {
                    SUCCES -> {

                        Log.d(TAG, "OK ${response.response.result.payUrl}")

                        startWebActivity(response.response.result.payUrl)
                        progress.dismiss()

                        idTransactionTextView.text = "${getString(R.string.last_transaction_id_text)} ${response.response.id}"
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

    private fun createUpdate3DSTransaction(id: Int, md: String, paRes: String){

        Log.d(TAG, "Authorisation OTP")

        val progress = showProgressDialog(this, "Loading...")

        CoroutineScope(Dispatchers.Main).launch {
            try {

                val response = appSDK.createUpdate3DSTransaction(id, md, paRes)

                when (response.status.code) {
                    SUCCES -> {

                        Log.d(TAG, "OK Transaction ${response.response.status}")
                        showToast("Transaction stutus ${response.response.status}")
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

    private fun startWebActivity(payUrl: String){
        val intent = Intent(this, WebActivity::class.java)
        intent.putExtra(WEB_URL , payUrl)
        startActivity(intent)
    }

    private fun startCardInputActivity(paySumm: String){
        val intent = Intent(this, CardInputActivity::class.java)
        intent.putExtra(PAY_SUMM , paySumm)
        startActivityForResult(intent, CARD_ACTIVITY);
    }

    private fun clearText(){
        idTransactionTextView.text = ""
        cardNumberTextView.text = ""
        cardTokenTextView.text = ""
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CARD_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val id: String? = data.getStringExtra(ID_TRANSACTION)
                idTransactionTextView.text = "${getString(R.string.last_transaction_id_text)} $id"
            }
        }
    }
}