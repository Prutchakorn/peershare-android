package com.example.prutc.peershare.data.remote.impl

import com.example.prutc.peershare.BuildConfig
import com.example.prutc.peershare.data.entity.BillSplit
import com.example.prutc.peershare.data.remote.TransactionApi
import com.example.prutc.peershare.util.registerHeaderForJson
import com.example.prutc.peershare.util.toObjects
import com.github.kittinunf.fuel.httpPost
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class TransactionApiImpl : TransactionApi {
    override fun list(promptPay: String): Single<List<BillSplit>> {
        registerHeaderForJson()
        val basePath = BuildConfig.BASE_URL
        return Single.create<List<BillSplit>> {
            "${basePath}api/getAllBillSplit?promptPay=$promptPay"
                .httpPost()
                .responseString { request, response, result ->
                    Timber.d(request.cUrlString())
                    Timber.d(response.data.toString())

                    result.fold({ billSplitList ->
                        it.onSuccess(billSplitList.toObjects(Array<BillSplit>::class.java))
                    }, { _ ->
                        it.onError(Throwable(response.statusCode.toString()))
                    })
                }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}