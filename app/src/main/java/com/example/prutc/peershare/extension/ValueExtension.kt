package com.example.prutc.peershare.extension

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.util.DisplayMetrics

fun Int.toResourceColor(context: Context) = ContextCompat.getColor(context, this)

fun Int.toDrawable(context: Context) = ContextCompat.getDrawable(context, this)

fun Int.toResourceFont(context: Context) = ResourcesCompat.getFont(context, this)

fun Float.convertDpToPixel(context: Context) =
    this * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

fun String.toHex() = "#${this.split(",")
    .filter { it.isNotBlank() }
    .map { it.toInt() }
    .joinToString(
        separator = "",
        transform = { Integer.toHexString(it).padStart(2, '0') })}"

fun String.toColor() = Color.parseColor(this)