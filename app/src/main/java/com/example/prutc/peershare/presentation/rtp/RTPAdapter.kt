package com.example.prutc.peershare.presentation.rtp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.prutc.peershare.R
import com.example.prutc.peershare.data.entity.Peer
import com.example.prutc.peershare.extension.toResourceColor
import kotlinx.android.synthetic.main.view_divided.view.*

class RTPAdapter(private val peers: List<Peer>) :
    RecyclerView.Adapter<RTPAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RTPAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_divided, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val peer = peers[position]
        val view = holder.view
        with(view) {
            tvName.text = peer.Name
            tvPrice.text = resources.getString(R.string.decimal_message, peer.PersonalTotalPrice)
            vPeerView.apply {
                init(peer) { _, _, _ -> }
                setPeerBackgroundColor(R.color.seventhary.toResourceColor(context))
            }
        }
    }

    override fun getItemCount() = peers.size
}