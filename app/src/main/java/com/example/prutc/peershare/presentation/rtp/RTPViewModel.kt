package com.example.prutc.peershare.presentation.rtp

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.prutc.peershare.data.entity.BillSplit
import com.example.prutc.peershare.data.entity.Peer
import com.example.prutc.peershare.data.remote.impl.RTPApiImpl
import com.example.prutc.peershare.data.repository.impl.RTPRepositoryImpl

class RTPViewModel: ViewModel() {

    var rtpRepositoryImp = RTPRepositoryImpl(RTPApiImpl())

    val state = State(
        rtpLoading = MutableLiveData()
    )

    val bill = MutableLiveData<BillSplit>()
    val errorMessage = MutableLiveData<Int>()

    fun post(billSplit: BillSplit) {
        state.rtpLoading.value =   true
        rtpRepositoryImp.post(billSplit).observeForever {
            state.rtpLoading.value = false
            val data = it?.data
            data?.let {
                bill.value = it
            } ?: {
                errorMessage.value = it?.statusCode
            }()
        }
    }

    data class State(
        val rtpLoading: MutableLiveData<Boolean>
    )
}