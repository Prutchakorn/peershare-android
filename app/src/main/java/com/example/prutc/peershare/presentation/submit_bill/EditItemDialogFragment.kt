package com.example.prutc.peershare.presentation.submit_bill

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import com.example.prutc.peershare.R
import com.example.prutc.peershare.data.entity.Item
import com.example.prutc.peershare.data.entity.Peer
import com.example.prutc.peershare.extension.toResourceColor
import kotlinx.android.synthetic.main.fragment_edit_item_dialog.view.*
import org.jetbrains.anko.support.v4.toast

class EditItemDialogFragment : DialogFragment() {

    companion object {
        fun newInstance(peerList: ArrayList<Peer>, item: Item, index: Int) = EditItemDialogFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList("peers", peerList)
                putParcelable("item", item)
                putInt("index", index)
            }
        }
    }

    private val peerList by lazy {
        arguments?.getParcelableArrayList<Peer>("peers")
    }

    private val item by lazy {
        arguments?.getParcelable<Item>("item")
    }

    private val index by lazy {
        arguments?.getInt("index")
    }

    private var sharedPeers = mutableListOf<Peer>()

    interface OnCompleteListener {
        fun onComplete(item: Item = Item(), index: Int, isDelete: Boolean)
    }

    lateinit var mListener: OnCompleteListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = createViewFragment()

        val alertDialog = AlertDialog.Builder(context).create().apply {
            setView(view)
            setCanceledOnTouchOutside(false)
            setCancelable(false)
            setDialogAnimation(window!!)
        }

        peerList?.let {
            view.vPeerViewGroup.apply {
                init(peerList = it.toList(),
                    peerBackgroundResource = R.drawable.bg_unselect_member,
                    textColor = R.color.enterContactUnSelectorColor.toResourceColor(context)) { vPeer, peer, isCheck ->
                    with(vPeer) {
                        if (!isCheck) {
                            sharedPeers.remove(peer)
                            setPeerBackgroundResource(R.drawable.bg_unselect_member)
                            setTextColor(R.color.enterContactUnSelectorColor.toResourceColor(context))
                        } else {
                            sharedPeers.add(peer)
                            setPeerBackgroundResource(R.drawable.bg_select_member)
                            setTextColor(R.color.enterContactSelectorColor.toResourceColor(context))
                        }
                    }
                }
            }
        }

        item?.let {
            view.etName.setText(it.name)
            view.etPrice.setText(it.price.toString())
        }

        view.cvDelete.setOnClickListener {
            index?.let {
                mListener.onComplete(index = it, isDelete = true)
            }
            alertDialog.dismiss()
        }

        view.cvSave.setOnClickListener {
            if (view.etPrice.text.toString().isNotEmpty()) {
                val name = view.etName.text.toString()
                val price = view.etPrice.text.toString().toDouble()
                val item = Item(name, sharedPeers, price)
                index?.let {
                    mListener.onComplete(item, it, false)
                }

            } else {
                toast("Save Failed")
            }
            alertDialog.dismiss()
        }

        view.ivCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        return alertDialog
    }

    private fun createViewFragment() = LayoutInflater.from(context).inflate(R.layout.fragment_edit_item_dialog, null, false)


    private fun setDialogAnimation(window: Window) {
        with(window) {
            setBackgroundDrawableResource(R.drawable.bg_edit_transaction_dialog)
            setGravity(Gravity.BOTTOM)
            attributes.windowAnimations = R.style.EditTransactionDialogAnimationStyle
//            attributes.y = 25
        }
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        this.mListener = activity as OnCompleteListener
    }
}
