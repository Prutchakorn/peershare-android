package com.example.prutc.peershare.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.prutc.peershare.R
import com.example.prutc.peershare.data.entity.Peer
import com.example.prutc.peershare.extension.inflate
import kotlinx.android.synthetic.main.view_peer.view.*
import org.jetbrains.anko.backgroundResource

class PeerView : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        inflate(R.layout.view_peer)
    }

    var isCheck = false

    fun init(peer: Peer, onClick: (PeerView, Peer, Boolean) -> Unit) {
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
            if (isCheck) {
                isCheck = false
                onClick(this, peer, isCheck)
            } else {
                isCheck = true
                onClick(this ,peer, isCheck)
            }
        }
    }

    fun setPeerBackgroundColor(color: Int) {
        cvPeer.setCardBackgroundColor(color)
    }

    fun setTextColor(color: Int) {
        tvName.setTextColor(color)
    }

    fun setPeerBackgroundResource(resId: Int) {
        cvPeer.setBackgroundResource(resId)
    }
}