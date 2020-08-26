package com.example.prutc.peershare.presentation.enter_bill_item

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.RadioButton
import com.example.prutc.peershare.R
import com.example.prutc.peershare.extension.convertDpToPixel
import com.example.prutc.peershare.extension.toDrawable
import kotlinx.android.synthetic.main.activity_enter_bill_item.*
import android.view.ViewGroup
import android.widget.RadioGroup
import com.example.prutc.peershare.extension.toResourceColor
import com.example.prutc.peershare.extension.toResourceFont

class EnterBillItemActivity : AppCompatActivity() {

    private var phoneNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_bill_item)
        val phoneNumberList = intent.getStringArrayListExtra("phoneNumberList")
        val size = 40f.convertDpToPixel(this)
        val fontFamily = R.font.proxima_nova_bold.toResourceFont(this)
        val params = RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            setMargins(0, 0, 8,0)
        }
        phoneNumberList.forEach {
            val rbMember = RadioButton(this).apply {
                setTextColor(R.color.enterBillNameUnSelectorColor.toResourceColor(context))
                text = it.takeLast(2)
                textSize = 12f
                typeface = fontFamily
                height = size.toInt()
                width = size.toInt()
                gravity = Gravity.CENTER
                buttonDrawable = null
                background = R.drawable.selector_member.toDrawable(context)
                layoutParams = params
                setOnCheckedChangeListener { compoundButton, isChecked ->
                    if (isChecked) {
                        setTextColor(R.color.enterBillNameSelectorColor.toResourceColor(context))
                    } else {
                        setTextColor(R.color.enterBillNameUnSelectorColor.toResourceColor(context))
                    }
                    val position = rgMember.indexOfChild(compoundButton)
                    phoneNumber = phoneNumberList[position]
                }
            }
            rgMember.addView(rbMember)
        }

        cvAdd.setOnClickListener {
            val price = etPrice.text.toString().toDouble()
            val intent = Intent().apply {
                putExtra("phoneNumber", phoneNumber)
                putExtra("price", price)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
