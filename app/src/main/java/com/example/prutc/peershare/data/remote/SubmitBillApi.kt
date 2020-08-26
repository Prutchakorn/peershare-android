package com.example.prutc.peershare.data.remote

import com.example.prutc.peershare.data.entity.BillSplit
import io.reactivex.Single
import java.io.File

interface SubmitBillApi {
    fun list(image: File): Single<List<Double>>
}