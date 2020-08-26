package com.example.prutc.peershare.extension

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton

fun Request.fetch(callback: (Request, Response, Result<String, FuelError>) -> Unit) {
    this.timeout(60000)
    this.responseString { req, res, result ->
        if (res.statusCode == 401) {
//            (context as AppCompatActivity).setFinishOnTouchOutside(false)
//            context.alert("Session Expired") {
//                isCancelable = false
//                okButton {
//                    //TODO Logout
//                }
//            }.show()
        } else {
            callback(req, res, result)
        }
    }
}