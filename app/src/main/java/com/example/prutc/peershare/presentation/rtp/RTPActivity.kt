package com.example.prutc.peershare.presentation.rtp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.prutc.peershare.R
import com.example.prutc.peershare.data.entity.BillSplit
import com.example.prutc.peershare.presentation.transaction.TransactionActivity
import com.example.prutc.peershare.util.toObject
import com.example.prutc.peershare.util.toObjects
import kotlinx.android.synthetic.main.activity_rtp.*
import org.jetbrains.anko.*

class RTPActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProviders.of(this)
            .get(RTPViewModel::class.java)
    }

    private val loadingDialog by lazy {
        indeterminateProgressDialog(resources.getString(R.string.loading_message)) { }
    }

    private val billSplit by lazy {
        intent.getStringExtra("billSplit").toObject<BillSplit>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rtp)

        initView()
        observeView()
        observeData()
    }

    private fun initView() {
        with(rvPeer) {
            layoutManager = LinearLayoutManager(this@RTPActivity, LinearLayoutManager.VERTICAL, false)
            adapter = RTPAdapter(billSplit.peers)
        }

        with(billSplit) {
            tvBillName.text = name!!.split(" ")[0]
            tvTotalPrice.text = resources.getString(R.string.decimal_message, totalPrice)
            tvPeople.text = numberOfPeople.toString()
            tvService.text = serviceCharge.toString()
            tvVat.text = vat.toString()

            cvRTP.setOnClickListener { view ->
                alert("Are you sure to send RTP. This process cannot be undone?") {
                    this.title = "RTP Request"
                    this.positiveButton("Confirm") {
                        viewModel.post(billSplit)
                    }
                    cancelButton {
                        it.dismiss()
                    }
                }.show()
            }
        }
        clBack.setOnClickListener {
            finish()
        }
    }

    private fun observeView() {
        viewModel.state.rtpLoading.observe(this, Observer { isLoading ->
            if (isLoading == true) {
                loadingDialog.show()
            } else {
                loadingDialog.dismiss()
            }
        })
    }

    private fun observeData() {
        viewModel.bill.observe(this, Observer { information ->
            information?.let {
                startActivity(intentFor<TransactionActivity>().clearTask().newTask())
            }
        })
    }
}
