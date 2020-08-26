package com.example.prutc.peershare.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.prutc.peershare.R
import com.example.prutc.peershare.data.entity.Item
import com.example.prutc.peershare.extension.inflate
import kotlinx.android.synthetic.main.view_item.view.*

class ItemView : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        inflate(R.layout.view_item)
    }

    private lateinit var item: Item
    private lateinit var onClick: (Item, Int) -> Unit
    private var index  = 0

    fun init(item: Item, index: Int, onClick: (Item, Int) -> Unit) {
        this.item = item
        this.onClick = onClick
        this.index = index
        renderItemView()
    }

    private fun renderItemView() {
        with(item) {
            tvName.text =  name
            tvPrice.text = resources.getString(R.string.decimal_message, price)
            vPeerViewGroup.init(peers, true) { _, _, _ -> }
        }
        setOnClickListener {
            onClick(item, index)
        }
    }
}