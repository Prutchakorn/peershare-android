package com.example.prutc.peershare.presentation.submit_bill

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.prutc.peershare.data.entity.BillSplit
import com.example.prutc.peershare.data.remote.impl.SubmitBillApiImpl
import com.example.prutc.peershare.data.repository.impl.SubmitBillRepositoryImpl
import java.io.File

class SubmitBillViewModel: ViewModel() {

    var createTransactionRepositoryImpl = SubmitBillRepositoryImpl(SubmitBillApiImpl())

    val state = State(
        createTransactionLoading = MutableLiveData()
    )

    val priceList = MutableLiveData<List<Double>>()
    val errorMessage = MutableLiveData<Int>()

    fun list(image: File) {
        state.createTransactionLoading.value =   true
        createTransactionRepositoryImpl.list(image).observeForever {
            state.createTransactionLoading.value = false
            val data = it?.data
            data?.let {
                priceList.value = it
            } ?: {
                errorMessage.value = it?.statusCode
            }()
        }
    }

    data class State(
        val createTransactionLoading: MutableLiveData<Boolean>
    )
}