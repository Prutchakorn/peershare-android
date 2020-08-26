package com.example.prutc.peershare.presentation.transaction

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.prutc.peershare.R
import com.example.prutc.peershare.extension.showProfileCircle
import com.example.prutc.peershare.presentation.create_transaction.CreateTransactionActivity
import com.example.prutc.peershare.presentation.transaction_detail.TransactionDetailActivity
import com.example.prutc.peershare.util.retrieveProfile
import kotlinx.android.synthetic.main.activity_transaction.*
import org.jetbrains.anko.startActivity

class TransactionActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this)
            .get(TransactionViewModel::class.java)
    }

    private val profile by lazy {
        retrieveProfile(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        initView()
        observeView()
        observeData()

        profile?.let {
            viewModel.list("0876831209")
//            viewModel.list(it.promptPay)
        }
    }

    private fun initView() {
        tvCreate.setOnClickListener {
            startActivity<CreateTransactionActivity>()
        }
        profile?.let {
            srRecent.setOnRefreshListener {
                viewModel.list("0876831209")
//                    viewModel.list(it.promptPay)
            }
            ivProfile.showProfileCircle(it.profileImageUrl)
        }
    }

    private fun observeView() {
        viewModel.state.transactionLoading.observe(this, Observer { isLoading ->
            srRecent.isRefreshing = isLoading == true
        })
    }

    private fun observeData() {
        viewModel.billSplitList.observe(this, Observer { billSplitList ->
            billSplitList?.let {
                with(rvRecent) {
                    layoutManager = LinearLayoutManager(this@TransactionActivity, LinearLayoutManager.VERTICAL, false)
                    adapter = TransactionAdapter(it.reversed()) { billSplit ->
                        startActivity<TransactionDetailActivity>("billSplit" to billSplit)
                    }
                }
            }
        })
    }
}
