package com.example.prutc.peershare.data.remote

import com.example.prutc.peershare.data.entity.BillSplit
import io.reactivex.Single

interface TransactionApi {
    fun list(promptPay: String): Single<List<BillSplit>>
}