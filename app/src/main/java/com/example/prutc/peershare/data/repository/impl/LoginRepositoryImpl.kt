package com.example.prutc.peershare.data.repository.impl

import android.arch.lifecycle.LiveData
import com.example.prutc.peershare.data.entity.Account
import com.example.prutc.peershare.data.remote.LoginApi
import com.example.prutc.peershare.data.repository.DataWrapper
import com.example.prutc.peershare.data.repository.LoginRepository

class LoginRepositoryImpl(val remoteSource: LoginApi) : LoginRepository {
    override fun login(promptPay: String, password: String): LiveData<DataWrapper<Boolean>> {
        return object : LiveData<DataWrapper<Boolean>>() {
            init {
                remoteSource.login(promptPay, password).subscribe({ isLogin ->
                    value = DataWrapper(
                        data = isLogin,
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
                        message = ""
                    )
                })
            }
        }
    }

    override fun getAccount(promptPay: String): LiveData<DataWrapper<Account>> {
        return object : LiveData<DataWrapper<Account>>() {
            init {
                remoteSource.getAccount(promptPay).subscribe({ account ->
                    value = DataWrapper(
                        data = account,
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
                        message = ""
                    )
                })
            }
        }
    }
}