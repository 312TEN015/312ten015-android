package com.fourleafclover.tarot.utils

import android.content.Context
import android.widget.Toast

class ToastUtil(val context: Context) {

    fun makeShortToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}