package com.example.prutc.peershare.extension

import java.text.SimpleDateFormat
import java.util.*

val defaultDateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault())

fun String.toDate() = defaultDateFormat.parse(this)