package com.example.prutc.peershare.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.prutc.peershare.R
import com.example.prutc.peershare.extension.inflate
import kotlinx.android.synthetic.main.view_large_member.view.*

class LargeMemberView : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        inflate(R.layout.view_large_member)
    }

    private var image = ""
    private var memberName = ""

    fun init(image: String, memberName: String) {
        this.image = image
        this.memberName = memberName
        renderMemberView()
    }

    private fun renderMemberView() {
        //TODO image
        tvPrice.text = memberName
    }
}