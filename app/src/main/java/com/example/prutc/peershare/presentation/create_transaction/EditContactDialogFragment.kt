package com.example.prutc.peershare.presentation.create_transaction

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import com.example.prutc.peershare.R
import com.example.prutc.peershare.data.entity.Peer
import com.example.prutc.peershare.extension.toDrawable
import com.example.prutc.peershare.extension.toResourceColor
import kotlinx.android.synthetic.main.fragment_edit_contact_dialog.view.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast

class EditContactDialogFragment : DialogFragment() {

    private var isChecked = true

    companion object {
        fun newInstance(peer: Peer, index: Int) = EditContactDialogFragment().apply {
            arguments = Bundle().apply {
                putParcelable("peer", peer)
                putInt("index", index)
            }
        }
    }

    interface OnCompleteListener {
        fun onComplete(peer: Peer = Peer(), index: Int, isDelete: Boolean)
    }

    private val peer by lazy {
        arguments?.getParcelable<Peer>("peer")
    }

    private val index by lazy {
        arguments?.getInt("index")
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

        peer?.let {
            view.etName.setText(it.Name)
            view.etPromptPay.setText(it.PromptPay)
        }

        with(view.cvCash) {
            tvCashTitle.setTextColor(R.color.enterContactUnSelectorColor.toResourceColor(context))
            this.backgroundDrawable = R.drawable.bg_unselect.toDrawable(context)
            context.toast(isChecked.toString())
            setOnClickListener {
                if (isChecked) {
                    tvCashTitle.setTextColor(R.color.enterContactSelectorColor.toResourceColor(context))
                    this.backgroundDrawable = R.drawable.bg_select.toDrawable(context)
                    isChecked = false
                    context.toast(isChecked.toString())
                } else {
                    tvCashTitle.setTextColor(R.color.enterContactUnSelectorColor.toResourceColor(context))
                    this.backgroundDrawable = R.drawable.bg_unselect.toDrawable(context)
                    isChecked = true
                    context.toast(isChecked.toString())
                }
            }
        }

        view.cvSave.setOnClickListener {
            if (view.etPromptPay.text.toString().isNotEmpty()) {
                val name = view.etName.text.toString()
                val promptPay = view.etPromptPay.text.toString()
                index?.let {
                    mListener.onComplete(Peer(Name = name, PromptPay = promptPay), it, false)
                }

            } else {
                toast("Edit Failed")
            }
            alertDialog.dismiss()
        }

        view.cvDelete.setOnClickListener {
            index?.let {
                mListener.onComplete(index = it, isDelete = true)
            }
            alertDialog.dismiss()
        }

        view.ivCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        return alertDialog
    }

    private fun createViewFragment() = LayoutInflater.from(context).inflate(R.layout.fragment_edit_contact_dialog, null, false)


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