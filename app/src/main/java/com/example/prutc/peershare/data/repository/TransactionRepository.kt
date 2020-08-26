package com.example.prutc.peershare.data.repository

import android.arch.lifecycle.LiveData
import com.example.prutc.peershare.data.entity.BillSplit

interface TransactionRepository {
    fun list(promptPay: String): LiveData<DataWrapper<List<BillSplit>>>
}