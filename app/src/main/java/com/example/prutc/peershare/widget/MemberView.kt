package com.example.prutc.peershare.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.prutc.peershare.R
import com.example.prutc.peershare.data.entity.Peer
import com.example.prutc.peershare.extension.inflate
import kotlinx.android.synthetic.main.view_member.view.*

class MemberView : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        inflate(R.layout.view_member)
    }

    fun init(peer: Peer, index: Int, onClick: (Peer, Int) -> Unit) {
        val nameList = peer.Name.split(" ")
        val firstName = nameList.getOrElse(0) { "" }.firstOrNull()
        val lastName = nameList.getOrElse(1) { "" }.firstOrNull()
        var memberName = ""
        firstName?.let {
            memberName += it
        }
        lastName?.let {
            memberName += it
        }
        tvName.text = memberName

        setOnClickListener {
            onClick(peer, index)
        }
    }
}