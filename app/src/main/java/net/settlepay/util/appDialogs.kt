package net.settlepay.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import net.settlepay.R
import net.settlepay.data.model.StatusTransaction

fun showProgressDialog(context: Context, message: String): Dialog {

    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_progress)
    dialog.window!!.setGravity(Gravity.CENTER)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    val progressTextTextView = dialog.findViewById(R.id.progressTextTextView) as TextView
    progressTextTextView.text = message
    dialog.show()
    return dialog
}

fun showInputDialog(context: Context, message: String, onClickOk: (String) -> Unit): Dialog{

    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_input)
    dialog.window!!.setGravity(Gravity.CENTER)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    val messageTextView = dialog.findViewById(R.id.messageTextView) as TextView
    messageTextView.text = message

    val transactionNumberEditText = dialog.findViewById(R.id.transactionNumberEditText) as EditText

    val okButton = dialog.findViewById(R.id.okTextView) as TextView
    okButton.setOnClickListener {

        if (transactionNumberEditText.text.isNotEmpty()){
            onClickOk(transactionNumberEditText.text.toString())
        }
        dialog.dismiss()
    }
    dialog.show()
    return dialog
}

fun showInputCardNumberDialog(context: Context, message: String, onClickOk: (String) -> Unit): Dialog{

    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_input_card)
    dialog.window!!.setGravity(Gravity.CENTER)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    val messageTextView = dialog.findViewById(R.id.messageTextView) as TextView
    messageTextView.text = message

    val cardNumberEditText = dialog.findViewById(R.id.cardNumberEditText) as EditText
    cardNumberEditText.addTextChangedListener(CreditCardTextFormatter())

    val okButton = dialog.findViewById(R.id.okTextView) as TextView
    okButton.setOnClickListener {

        if (cardNumberEditText.text.isNotEmpty()){
            onClickOk(cardNumberEditText.text.toString().replace(" ",""))
        }
        dialog.dismiss()
    }
    dialog.show()
    return dialog
}

fun showDoubleInputDialog(context: Context, message: String, onClickOk: (StatusTransaction) -> Unit): Dialog{

    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_double_input)
    dialog.window!!.setGravity(Gravity.CENTER)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    val messageTextView = dialog.findViewById(R.id.messageTextView) as TextView
    messageTextView.text = message

    val transactionNumberEditText = dialog.findViewById(R.id.transactionNumberEditText) as EditText
    val mdNumberEditText          = dialog.findViewById(R.id.mdNumberEditText) as EditText
    val paResNumberEditText       = dialog.findViewById(R.id.paResNumberEditText) as EditText

    val okButton = dialog.findViewById(R.id.okTextView) as TextView
    okButton.setOnClickListener {

        if (transactionNumberEditText.text.isNotEmpty() && mdNumberEditText.text.isNotEmpty() && paResNumberEditText.text.isNotEmpty()){

            onClickOk(
                StatusTransaction(
                    transactionNumberEditText.text.toString(),
                    mdNumberEditText.text.toString(),
                    paResNumberEditText.text.toString()
                )
            )
        }
        dialog.dismiss()
    }
    dialog.show()
    return dialog
}



