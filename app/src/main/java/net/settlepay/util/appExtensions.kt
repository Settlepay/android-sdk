package net.settlepay.util

import android.content.Context
import android.widget.Toast

internal fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}