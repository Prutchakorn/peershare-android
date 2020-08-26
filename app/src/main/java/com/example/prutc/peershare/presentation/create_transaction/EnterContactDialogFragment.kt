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
import kotlinx.android.synthetic.main.fragment_enter_contact_dialog.view.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast

class EnterContactDialogFragment : DialogFragment() {

    private var isChecked = true

    companion object {
        fun newInstance() = EnterContactDialogFragment()
    }

    interface OnCompleteListener {
        fun onComplete(peer: Peer, isCash: Boolean)
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

        view.cvAdd.setOnClickListener {
            if (view.etPromptPay.text.toString().isNotEmpty()) {
                val name = view.etName.text.toString()
                val promptPay = view.etPromptPay.text.toString()
                mListener.onComplete(Peer(Name = name, PromptPay =  promptPay), isChecked)
            } else {
                toast(R.string.enter_contact_add_failed)
            }
            alertDialog.dismiss()
        }

        view.ivCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        return alertDialog
    }

    private fun createViewFragment() = LayoutInflater.from(context).inflate(R.layout.fragment_enter_contact_dialog, null, false)


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
