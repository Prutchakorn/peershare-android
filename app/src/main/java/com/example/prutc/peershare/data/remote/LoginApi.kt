package com.example.prutc.peershare.data.remote

import com.example.prutc.peershare.data.entity.Account
import io.reactivex.Single

interface LoginApi {
    fun login(promptPay: String, password: String): Single<Boolean>
    fun getAccount(promptPay: String): Single<Account>
}