package com.example.prutc.peershare.presentation.transaction_detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.prutc.peershare.R
import com.example.prutc.peershare.data.entity.BillSplit
import kotlinx.android.synthetic.main.activity_transaction_detail.*

class TransactionDetailActivity : AppCompatActivity() {

    private val billSplit by lazy {
        intent.getParcelableExtra<BillSplit>("billSplit")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_detail)

        initView()
    }

    private fun initView() {
        ivCancel.setOnClickListener {
            finish()
        }
        with(billSplit) {
            tvPeople.text = numberOfPeople.toString()
            tvService.text = serviceCharge.toString()
            tvVat.text = vat.toString()
            tvBillName.text =  name
            tvTotalPrice.text = resources.getString(R.string.decimal_message, totalPrice)
        }
        rvBillSplit.apply {
            layoutManager = LinearLayoutManager(this@TransactionDetailActivity, LinearLayoutManager.VERTICAL, false)
            adapter = TransactionDetailAdapter(billSplit.peers)
        }
    }
}
