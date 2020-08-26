package com.example.prutc.peershare.util

import com.github.kittinunf.fuel.core.FuelManager

fun registerHeaderForUploading() {
    FuelManager.instance.baseHeaders = mapOf(
        "Content-Type" to "multipart/form-data;boundary=${System.currentTimeMillis().toString(36)}")
}

fun registerHeaderForJson() {
    FuelManager.instance.baseHeaders = mapOf(
        "Content-Type" to "application/json;boundary=${System.currentTimeMillis().toString(36)}")
}