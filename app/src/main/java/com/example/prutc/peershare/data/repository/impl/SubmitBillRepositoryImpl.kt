package com.example.prutc.peershare.data.repository.impl

import android.arch.lifecycle.LiveData
import com.example.prutc.peershare.data.entity.BillSplit
import com.example.prutc.peershare.data.remote.SubmitBillApi
import com.example.prutc.peershare.data.repository.SubmitBillRepository
import com.example.prutc.peershare.data.repository.DataWrapper
import java.io.File

class SubmitBillRepositoryImpl(val remoteSource: SubmitBillApi) : SubmitBillRepository {
    override fun list(image: File): LiveData<DataWrapper<List<Double>>> {
        return object : LiveData<DataWrapper<List<Double>>>() {
            init {
                remoteSource.list(image).subscribe({ priceList ->
                    value = DataWrapper(
                        data = priceList,
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