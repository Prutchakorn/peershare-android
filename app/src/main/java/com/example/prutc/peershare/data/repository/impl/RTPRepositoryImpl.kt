package com.example.prutc.peershare.data.repository.impl

import android.arch.lifecycle.LiveData
import com.example.prutc.peershare.data.entity.BillSplit
import com.example.prutc.peershare.data.entity.Peer
import com.example.prutc.peershare.data.remote.RTPApi
import com.example.prutc.peershare.data.repository.DataWrapper
import com.example.prutc.peershare.data.repository.RTPRepository

class RTPRepositoryImpl(val remoteSource: RTPApi) : RTPRepository {
    override fun post(billSplit: BillSplit): LiveData<DataWrapper<BillSplit>> {
        return object : LiveData<DataWrapper<BillSplit>>() {
            init {
                remoteSource.post(billSplit).subscribe({ billSplit ->
                    value = DataWrapper(
                        data = billSplit,
                        error = null,
                        statusCode = null,
                        message = null
                    )
                }, { error ->
                    val statusCode = error.message.toString().toInt()
                    value = DataWrapper(
                        data = null,
                        error = error,
                        statusCode = statusCode,
                        message =""
                    )
                })
            }
        }
    }
}