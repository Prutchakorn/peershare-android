package com.example.prutc.peershare.data.repository

import android.arch.lifecycle.LiveData
import com.example.prutc.peershare.data.entity.BillSplit
import com.example.prutc.peershare.data.entity.Peer

interface RTPRepository {
    fun post(billSplit: BillSplit): LiveData<DataWrapper<BillSplit>>
}