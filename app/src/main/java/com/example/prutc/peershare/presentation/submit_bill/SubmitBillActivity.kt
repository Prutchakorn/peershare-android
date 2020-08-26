package com.example.prutc.peershare.presentation.submit_bill

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.example.prutc.peershare.R
import com.example.prutc.peershare.data.entity.BillSplit
import com.example.prutc.peershare.data.entity.Item
import com.example.prutc.peershare.data.entity.Peer
import com.example.prutc.peershare.extension.showProfileCircle
import com.example.prutc.peershare.presentation.rtp.RTPActivity
import com.example.prutc.peershare.util.retrieveProfile
import com.example.prutc.peershare.util.toJson
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_submit_bill.*
import kotlinx.android.synthetic.main.view_large_member.*
import org.jetbrains.anko.*
import java.io.File

class SubmitBillActivity : AppCompatActivity(),
    EnterItemDialogFragment.OnCompleteListener,
    EditItemDialogFragment.OnCompleteListener {

    private val viewModel by lazy {
        ViewModelProviders.of(this)
            .get(SubmitBillViewModel::class.java)
    }
    private val loadingDialog by lazy {
        indeterminateProgressDialog(resources.getString(R.string.loading_message)) { }
    }
    private val billName by lazy {
        intent.getStringExtra("billName")
    }

    private val billTotal by lazy {
        intent.getDoubleExtra("billTotal", 0.0)
    }

    private val split by lazy {
        intent.getIntExtra("split", 0)
    }

    private val peerList by lazy {
        intent.getParcelableArrayListExtra<Peer>("peers")
    }

    private var items = arrayListOf<Item>()

    private var paid = 0.0

    private val profile by lazy {
        retrieveProfile(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit_bill)

        initView()
        observeView()
        observeData()
    }

    private fun initView() {

        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (permission == PackageManager.PERMISSION_DENIED) {
            requestPermission()
        }
        cvScan.setOnClickListener {
            openCamera()
        }
        clBack.setOnClickListener {
            alert("Are you sure that you want to delete these items?") {
                this.title = "Warning"
                this.positiveButton("Delete") {
                    finish()
                }
                cancelButton {
                    it.dismiss()
                }
            }.show()
        }

        tvEnterManually.setOnClickListener {
            EnterItemDialogFragment.newInstance(peerList).show(supportFragmentManager, "Member Information")
        }

        cvSubmit.setOnClickListener {
            profile?.let {
                if (items.isNotEmpty()) {
                    val currentPeers = mutableListOf<Peer>()
                    items.forEach { item ->
                        //TODO split
                        currentPeers.addAll(item.peers.map { peer -> peer.apply {
                            PersonalTotalPrice += item.price / item.peers.size
                        } })

                    }
                    val peers = currentPeers.groupBy { it.Name }.map { it.value[0] }

                    val billSplit = BillSplit(
                        ownerPromptPay = it.promptPay,
                        totalPrice = billTotal,
                        isActive = true,
                        peers = peers,
                        name = billName
                    )
                    startActivity<RTPActivity>("billSplit" to billSplit.toJson())
                } else {
                       toast("item is empty")
                }
            }
        }
        tvBillName.text = billName
        tvRemaining.text = resources.getString(R.string.decimal_message, billTotal)
        tvPaid.text = resources.getString(R.string.decimal_message, paid)
    }

    private fun observeView() {
        viewModel.state.createTransactionLoading.observe(this, Observer { isLoading ->
            if (isLoading == true) {
                loadingDialog.show()
            } else {
                loadingDialog.dismiss()
            }
        })
    }

    private fun observeData() {
        viewModel.priceList.observe(this, Observer { profile ->
            profile?.let {
                val newItemList = arrayListOf<Item>()
                it.forEach {
                    val item = Item(price = it)
                    newItemList.add(item)
                }
                items.addAll(newItemList)
                vItemViewGroup.init(items) { item, index ->
                    EditItemDialogFragment.newInstance(peerList, item, index).show(supportFragmentManager, "Member Information")
                }
                paid = items.sumByDouble { it.price }
                tvPaid.text = resources.getString(R.string.decimal_message, paid)
                tvRemaining.text = resources.getString(R.string.decimal_message, (billTotal - paid))
            }
        })
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            toast("CAMERA permission allows us to access CAMERA app")
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                toast("Permission Granted")
            } else {
                toast("Permission Canceled")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val path = result.uri.path
                path?.let {
                    val file = File(it)
                    viewModel.list(file)
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                alert(resources.getString(R.string.general_alertmessage_somethingwentwrong_title)) {
                    okButton {

                    }
                }.show()
            }
        } else {
            if (resultCode == Activity.RESULT_OK) {
//                data?.let {
//                    val phoneNumber = it.getStringExtra("phoneNumber")
//                    val price = it.getDoubleExtra("price", 0.0)
//                    val item = Item(
//                        phoneNumber = phoneNumber,
//                        price = price
//                    )
//                    items.add(item)
//                    vItemViewGroup.init(items)
//                }
            }
        }
    }

    private fun openCamera() {
        CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON)
            .start(this)
    }

    override fun onComplete(item: Item) {
        items.add(item)
        vItemViewGroup.init(items) { item, index ->
            EditItemDialogFragment.newInstance(peerList, item, index).show(supportFragmentManager, "Member Information")
        }
        paid = items.sumByDouble { it.price }
        tvPaid.text = resources.getString(R.string.decimal_message, paid)
        tvRemaining.text = resources.getString(R.string.decimal_message, (billTotal - paid))
    }

    override fun onComplete(item: Item, index: Int, isDelete: Boolean) {
        if (isDelete) {
            items.removeAt(index)
        } else {
            items.removeAt(index)
            items.add(index, item)
        }
        vItemViewGroup.init(items) { item, index ->
            EditItemDialogFragment.newInstance(peerList, item, index).show(supportFragmentManager, "Member Information")
        }
        paid = items.sumByDouble { it.price }
        tvPaid.text = resources.getString(R.string.decimal_message, paid)
        tvRemaining.text = resources.getString(R.string.decimal_message, (billTotal - paid))
    }
}
