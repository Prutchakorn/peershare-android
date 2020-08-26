package com.example.prutc.peershare.presentation.create_transaction

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import com.example.prutc.peershare.R
import com.example.prutc.peershare.data.entity.Peer
import com.example.prutc.peershare.extension.toResourceColor
import com.example.prutc.peershare.presentation.submit_bill.SubmitBillActivity
import com.example.prutc.peershare.util.getPeer
import kotlinx.android.synthetic.main.activity_create_transaction.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class CreateTransactionActivity : AppCompatActivity(),
    EnterContactDialogFragment.OnCompleteListener,
    EditContactDialogFragment.OnCompleteListener {

    private var peers = arrayListOf<Peer>()
    private var split = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_transaction)

        initView()
    }

    private fun initView() {
        tvCancel.setOnClickListener {
            finish()
        }

        ivAddByContact.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            startActivityForResult(intent, 99)
        }

        ivAddMember.setOnClickListener {
            EnterContactDialogFragment.newInstance().show(supportFragmentManager, "Member Information")
        }

        cvNext.setOnClickListener {
            val billName = etBillName.text.toString()
            val billTotal = etBillTotal.text.toString()
            if (billName.isNotEmpty() && billTotal.isNotEmpty() && peers.isNotEmpty()) {
                startActivity<SubmitBillActivity>(
                    "billName" to billName,
                    "billTotal" to billTotal.toDouble(),
                    "split" to split,
                    "peers" to peers
                )
            } else {
                toast("Please! fill your information or select your friends")
            }

        }
        renderSplitBill()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       if (requestCode == 99) {
            if (resultCode == Activity.RESULT_OK) {
                data?.data?.let {
                    val peer = getPeer(this, it)
                    peers.add(peer)
                    vMemberViewGroup.init(peers) { peer, index ->
                        EditContactDialogFragment.newInstance(peer, index).show(supportFragmentManager, "Member Information")
                    }
                }
            }
        }
    }

    override fun onComplete(peer: Peer, isCash: Boolean) {
        peers.add(peer)
        vMemberViewGroup.init(peers) { peer, index ->
            EditContactDialogFragment.newInstance(peer, index).show(supportFragmentManager, "Member Information")
        }
    }

    override fun onComplete(peer: Peer, index: Int, isDelete: Boolean) {
        if (isDelete) {
            peers.removeAt(index)
        } else {
            peers.removeAt(index)
            peers.add(index, peer)
        }
        vMemberViewGroup.init(peers) { peer, index ->
            EditContactDialogFragment.newInstance(peer, index).show(supportFragmentManager, "Member Information")
        }
    }

    private fun renderSplitBill() {
        rgBillSplit.check(rbSplitEqually.id)
        with(rbSplitEqually) {
            this.setTextColor(R.color.createTransactionSelectorColor.toResourceColor(this@CreateTransactionActivity))
            backgroundResource = R.drawable.selector
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    split = 0
                    this.setTextColor(R.color.createTransactionSelectorColor.toResourceColor(this@CreateTransactionActivity))
                } else {
                    this.setTextColor(R.color.createTransactionUnSelectorColor.toResourceColor(this@CreateTransactionActivity))
                }
            }
        }
        with(rbSplitByItem) {
            backgroundResource = R.drawable.selector
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    split = 1
                    this.setTextColor(R.color.createTransactionSelectorColor.toResourceColor(this@CreateTransactionActivity))
                } else {
                    this.setTextColor(R.color.createTransactionUnSelectorColor.toResourceColor(this@CreateTransactionActivity))
                }
            }
        }
    }
}
