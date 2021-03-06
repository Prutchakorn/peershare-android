package com.example.prutc.peershare.presentation.transaction_detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.prutc.peershare.R
import com.example.prutc.peershare.data.entity.Peer
import com.example.prutc.peershare.extension.toColor
import com.example.prutc.peershare.extension.toHex
import com.example.prutc.peershare.extension.toResourceColor
import kotlinx.android.synthetic.main.view_bill.view.*

class TransactionDetailAdapter(private val peerList: List<Peer>) :
    RecyclerView.Adapter<TransactionDetailAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionDetailAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_bill, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val peer = peerList[position]
        val view = holder.view
        with(view) {
            tvName.text = peer.Name
            tvPrice.text = resources.getString(R.string.decimal_message, peer.PersonalTotalPrice)
            vPeerView.apply {
                init(peer) { _, _, _ -> }
                setPeerBackgroundColor(R.color.primary.toResourceColor(context))
            }
            cvStatus.setCardBackgroundColor(peer.Status.ColorCode.toColor())
        }
    }

    override fun getItemCount() = peerList.size
}