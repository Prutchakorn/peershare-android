package com.example.prutc.peershare.data.remote

import com.example.prutc.peershare.data.entity.BillSplit
import com.example.prutc.peershare.data.entity.Peer
import io.reactivex.Single

interface RTPApi {
    fun post(billSplit: BillSplit): Single<BillSplit>
}