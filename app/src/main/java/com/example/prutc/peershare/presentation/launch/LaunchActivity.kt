package com.example.prutc.peershare.presentation.launch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.prutc.peershare.R
import com.example.prutc.peershare.presentation.login.LoginActivity
import kotlinx.android.synthetic.main.activity_launch.*
import org.jetbrains.anko.startActivity

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        initView()
    }

    private fun initView() {
        tvLogin.setOnClickListener {
            startActivity<LoginActivity>()
        }
    }
}
