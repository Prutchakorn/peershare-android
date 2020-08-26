package com.example.prutc.peershare.data.repository

import android.arch.lifecycle.LiveData
import com.example.prutc.peershare.data.entity.BillSplit
import java.io.File

interface SubmitBillRepository {
    fun list(image: File): LiveData<DataWrapper<List<Double>>>
}