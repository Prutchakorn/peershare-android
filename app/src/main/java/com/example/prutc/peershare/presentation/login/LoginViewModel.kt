package com.example.prutc.peershare.presentation.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.prutc.peershare.data.entity.Account
import com.example.prutc.peershare.data.remote.impl.LoginApiImpl
import com.example.prutc.peershare.data.repository.impl.LoginRepositoryImpl

class LoginViewModel: ViewModel() {

    var loginRepositoryImpl = LoginRepositoryImpl(LoginApiImpl())

    val state = State(
        loginLoading = MutableLiveData()
    )

    val isLogin = MutableLiveData<Boolean>()
    val account = MutableLiveData<Account>()
    val errorMessage = MutableLiveData<Int>()

    fun login(promptPay: String, password: String) {
        state.loginLoading.value =   true
        loginRepositoryImpl.login(promptPay, password).observeForever {
            state.loginLoading.value = false
            val data = it?.data
            data?.let {
                isLogin.value = it
            } ?: {
                errorMessage.value = it?.statusCode
            }()
        }
    }

    fun getAccount(promptPay: String) {
        state.loginLoading.value =   true
        loginRepositoryImpl.getAccount(promptPay).observeForever {
            state.loginLoading.value = false
            val data = it?.data
            data?.let {
                account.value = it
            } ?: {
                errorMessage.value = it?.statusCode
            }()
        }
    }

    data class State(
        val loginLoading: MutableLiveData<Boolean>
    )
}