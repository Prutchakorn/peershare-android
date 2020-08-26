package com.example.prutc.peershare.data.repository.impl

import android.arch.lifecycle.LiveData
import com.example.prutc.peershare.data.entity.BillSplit
import com.example.prutc.peershare.data.remote.TransactionApi
import com.example.prutc.peershare.data.repository.DataWrapper
import com.example.prutc.peershare.data.repository.TransactionRepository

class TransactionRepositoryImpl(val remoteSource: TransactionApi) : TransactionRepository {
    override fun list(promptPay: String): LiveData<DataWrapper<List<BillSplit>>> {
        return object : LiveData<DataWrapper<List<BillSplit>>>() {
            init {
                remoteSource.list(promptPay).subscribe({ billSplitList ->
                    value = DataWrapper(
                        data = billSplitList,
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