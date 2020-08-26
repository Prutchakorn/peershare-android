package com.example.prutc.peershare.presentation.login

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class PhoneNumberFormatWatcher(private val etPhoneNumber: EditText) : TextWatcher {
    private var isDeleted = false

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        isDeleted = (before == 0).not()
    }

    override fun afterTextChanged(s: Editable) {
        val source = s.toString()
        val length = source.length
        val stringBuilder = StringBuilder()
        stringBuilder.append(source)
        if (length > 0) {
            when(length) {
                4 -> insertText(stringBuilder, length)
                8 -> insertText(stringBuilder, length)
            }
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    private fun insertText(stringBuilder: java.lang.StringBuilder, length: Int) {
        if (isDeleted) {
            stringBuilder.deleteCharAt(length - 1)
        } else {
            stringBuilder.insert(length - 1, "-")
        }
        with(etPhoneNumber) {
            setText(stringBuilder)
            setSelection(text.length)
        }
    }
}