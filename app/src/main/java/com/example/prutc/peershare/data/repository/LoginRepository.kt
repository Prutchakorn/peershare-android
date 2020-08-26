package com.example.prutc.peershare.data.repository

import android.arch.lifecycle.LiveData
import com.example.prutc.peershare.data.entity.Account

interface LoginRepository {
    fun login(promptPay: String, password: String): LiveData<DataWrapper<Boolean>>
    fun getAccount(promptPay: String): LiveData<DataWrapper<Account>>
}