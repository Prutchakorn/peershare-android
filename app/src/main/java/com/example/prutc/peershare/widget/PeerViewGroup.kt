package com.example.prutc.peershare.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.prutc.peershare.R
import com.example.prutc.peershare.data.entity.Peer
import com.example.prutc.peershare.extension.inflate
import com.example.prutc.peershare.extension.toResourceColor
import kotlinx.android.synthetic.main.view_peer_group.view.*
import org.jetbrains.anko.backgroundResource

class PeerViewGroup : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        inflate(R.layout.view_peer_group)
    }

    fun init(
        peerList: List<Peer>,
        isItem: Boolean = false,
        backgroundColor: Int = R.color.submitItemPeerBackgroundColor.toResourceColor(context),
        textColor: Int = R.color.secondary.toResourceColor(context),
        peerBackgroundResource: Int = 0,
        onClick: (PeerView, Peer, Boolean) -> Unit
    ) {
        llPeer.removeAllViews()
        peerList.forEach {
            llPeer.addView(PeerView(context).apply {
                init(it, onClick)
                setTextColor(textColor)
                setPeerBackgroundColor(backgroundColor)

                if (peerBackgroundResource != 0) {
                    setPeerBackgroundResource(peerBackgroundResource)
                }
            })
        }
    }
}