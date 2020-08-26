package com.example.prutc.peershare.presentation.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.prutc.peershare.R
import com.example.prutc.peershare.presentation.transaction.TransactionActivity
import com.example.prutc.peershare.util.saveProfile
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this)
            .get(LoginViewModel::class.java)
    }
    private val loadingDialog by lazy {
        indeterminateProgressDialog(resources.getString(R.string.loading_message)) { }
    }

    private var promptPay = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
        observeView()
        observeData()
    }

    private fun initView() {
        vPin?.let {
            it.setOnPinEnteredListener { pin ->
                promptPay = etPromptPay.text.toString()
                if (pin.toString().length == 4 && promptPay.isNotEmpty()) {
                    viewModel.login(promptPay, pin.toString())
                } else if (pin.toString() == "1111") {
//                    startActivity<TransactionActivity>()
                } else {
                    toast(resources.getString(R.string.login_phone_number_failed))
                    vPin.text = null
                }
            }
        }
    }

    private fun observeView() {
        viewModel.state.loginLoading.observe(this, Observer { isLoading ->
            if (isLoading == true) {
                loadingDialog.show()
            } else {
                loadingDialog.dismiss()
            }
        })
    }

    private fun observeData() {
        viewModel.isLogin.observe(this, Observer { isLogin ->
            isLogin?.let {
                if(it) {
                    viewModel.getAccount(promptPay)
                } else {
                    toast("Get Account Failed")
                }
            }
        })
        viewModel.account.observe(this, Observer { account ->
            account?.let {
                saveProfile(this, it)
                startActivity<TransactionActivity>()
            }
        })
    }
}
