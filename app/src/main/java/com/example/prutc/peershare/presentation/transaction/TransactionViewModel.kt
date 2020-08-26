package com.example.prutc.peershare.presentation.transaction

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.prutc.peershare.data.entity.BillSplit
import com.example.prutc.peershare.data.remote.impl.TransactionApiImpl
import com.example.prutc.peershare.data.repository.impl.TransactionRepositoryImpl

class TransactionViewModel: ViewModel() {

    var transactionRepositoryImpl = TransactionRepositoryImpl(TransactionApiImpl())

    val state = State(
        transactionLoading = MutableLiveData()
    )

    val billSplitList = MutableLiveData<List<BillSplit>>()
    val errorMessage = MutableLiveData<Int>()

    fun list(promptPay: String) {
        state.transactionLoading.value =   true
        transactionRepositoryImpl.list(promptPay).observeForever {
            state.transactionLoading.value = false
            val data = it?.data
            data?.let {
                billSplitList.value = it
            } ?: {
                errorMessage.value = it?.statusCode
            }()
        }
    }

    data class State(
        val transactionLoading: MutableLiveData<Boolean>
    )
}