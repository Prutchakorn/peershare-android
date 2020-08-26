package com.example.prutc.peershare.data.remote.impl

import com.example.prutc.peershare.BuildConfig
import com.example.prutc.peershare.data.entity.BillSplit
import com.example.prutc.peershare.data.entity.Peer
import com.example.prutc.peershare.data.entity.PeerSample
import com.example.prutc.peershare.data.remote.RTPApi
import com.example.prutc.peershare.util.registerHeaderForJson
import com.example.prutc.peershare.util.toJson
import com.example.prutc.peershare.util.toObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.google.gson.JsonObject
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class RTPApiImpl : RTPApi {
    override fun post(billSplit: BillSplit): Single<BillSplit> {
        registerHeaderForJson()
        val basePath = BuildConfig.BASE_URL
        return Single.create<BillSplit> {
            with(billSplit) {
                val peers = mutableListOf<PeerSample>()
                peers.addAll(billSplit.peers.map { peer -> PeerSample(
                    Name = peer.Name,
                    PromptPay = peer.PromptPay,
                    PersonalTotalPrice = peer.PersonalTotalPrice
                ) })
                "${basePath}api/postBillSplit"
                    .httpPost()
                    .body(
                        """
                                        {
                                            "OwnerPromptPay": "$ownerPromptPay",
                                            "TotalPrice": $totalPrice,
                                            "Name": "$name",
                                            "Peers": ${peers.toJson()}
                                        }
                                        """
                    )
                    .responseString { request, response, result ->
                        Timber.d(request.cUrlString())
                        Timber.d(response.data.toString())
                        Timber.d(result.get())

                        result.fold({ priceList ->
                            it.onSuccess(priceList.toObject())
                        }, { error ->
                            it.onError(error)
                        })
                    }
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}