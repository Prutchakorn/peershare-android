package com.example.prutc.peershare.data.remote.impl

import com.example.prutc.peershare.BuildConfig
import com.example.prutc.peershare.data.entity.Account
import com.example.prutc.peershare.data.remote.LoginApi
import com.example.prutc.peershare.util.registerHeaderForJson
import com.example.prutc.peershare.util.toObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class LoginApiImpl : LoginApi {
    val basePath = BuildConfig.BASE_URL
    override fun login(promptPay: String, password: String): Single<Boolean> {
        registerHeaderForJson()
        return Single.create<Boolean> {
            "${basePath}api/HealthCheck"
//            "${basePath}Account/Login"
                .httpGet()
//                    .httpPost()
//                    .body("""
//                                        {
//                                            "promptPay": "$promptPay",
//                                            "password": "$password"
//                                        }
//                                        """)
                .responseString { request, response, result ->
                    Timber.d(request.cUrlString())
                    Timber.d(response.data.toString())
                    Timber.d(result.toString())
//                    result.fold({ isLogin ->
//                        it.onSuccess(isLogin.toBoolean())
//                    }, { _ ->
//                        it.onError(Throwable(response.statusCode.toString()))
//                    })
                }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getAccount(promptPay: String): Single<Account> {
        registerHeaderForJson()
        return Single.create<Account> {
            "${basePath}api/getAccount?promptpay=$promptPay"
                .httpPost()
                .responseString { request, response, result ->
                    Timber.d(request.cUrlString())
                    Timber.d(response.data.toString())

                    result.fold({ account ->
                        it.onSuccess(account.toObject())
                    }, { _ ->
                        it.onError(Throwable(response.statusCode.toString()))
                    })
                }
        }
    }
}