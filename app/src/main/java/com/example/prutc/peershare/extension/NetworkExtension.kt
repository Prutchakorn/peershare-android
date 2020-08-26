package com.example.prutc.peershare.extension

import android.content.Context
import com.example.prutc.peershare.R

fun Int.toErrorMessage(context: Context) = when (this) {
    400 -> context.resources.getString(R.string.general_alertmessage_serviceerror_400_title)
    401 -> context.resources.getString(R.string.general_alertmessage_serviceerror_401_title)
    404 -> context.resources.getString(R.string.general_alertmessage_serviceerror_404_title)
    500 -> context.resources.getString(R.string.general_alertmessage_serviceerror_500_title)
    else -> context.resources.getString(R.string.general_alertmessage_somethingwentwrong_title)
}