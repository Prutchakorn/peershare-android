package com.example.prutc.peershare.extension

import java.text.SimpleDateFormat
import java.util.*

fun Date.toBirthDate() = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault()).format(this)