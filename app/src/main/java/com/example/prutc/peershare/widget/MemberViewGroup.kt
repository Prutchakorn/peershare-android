package com.example.prutc.peershare.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.prutc.peershare.R
import com.example.prutc.peershare.data.entity.Peer
import com.example.prutc.peershare.extension.inflate
import kotlinx.android.synthetic.main.view_member_group.view.*

class MemberViewGroup : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        inflate(R.layout.view_member_group)
    }

    fun init(peerList: List<Peer>, onClick: (Peer, Int) -> Unit) {
        llMember.removeAllViews()
        peerList.forEachIndexed { index, peer ->
            llMember.addView(MemberView(context).apply {
                init(peer, index, onClick)
            })
        }
    }
}