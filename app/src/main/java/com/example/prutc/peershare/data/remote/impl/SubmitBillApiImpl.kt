package com.example.prutc.peershare.data.remote.impl

import com.example.prutc.peershare.BuildConfig
import com.example.prutc.peershare.data.entity.BillSplit
import com.example.prutc.peershare.data.entity.PeerSample
import com.example.prutc.peershare.data.remote.SubmitBillApi
import com.example.prutc.peershare.util.*
import com.github.kittinunf.fuel.core.DataPart
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpUpload
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.File

class SubmitBillApiImpl : SubmitBillApi {
    override fun list(image: File): Single<List<Double>> {
        registerHeaderForUploading()
        val basePath = BuildConfig.BASE_URL
        return Single.create<List<Double>> {
            val dataPart = DataPart(image, "file")
            ("${basePath}image/detecttext"
                .httpUpload()
                .dataParts { _, _ -> listOf(dataPart) }
                .responseString { request, response, result ->
                    Timber.d(request.cUrlString())
                    Timber.d(response.data.toString())
                    Timber.d(result.get())

                    result.fold({ priceList ->
                        it.onSuccess(priceList.toObjects(Array<Double>::class.java))
                    }, { error ->
                        it.onError(error)
                    })
                })
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
