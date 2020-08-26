package com.example.prutc.peershare.presentation.transaction

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.prutc.peershare.R
import com.example.prutc.peershare.data.entity.BillSplit
import com.example.prutc.peershare.extension.toResourceColor
import kotlinx.android.synthetic.main.view_recent.view.*

class TransactionAdapter(private val recentList: List<BillSplit>, private val onClick: (BillSplit) -> Unit) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_recent, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recent = recentList[position]
        val view = holder.view
        with(view) {
            tvBillName.text = recent.name
            tvAmount.text = resources.getString(R.string.decimal_message, recent.totalPrice)
            tvStatus.text = "Pending"
            vPeerViewGroup.init(
                peerList = recent.peers,
                backgroundColor = R.color.primary.toResourceColor(context)
            ) { _, _, _ -> }

            setOnClickListener {
                onClick(recent)
            }
        }
    }

    override fun getItemCount() = recentList.size
}