package com.example.prutc.peershare.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.prutc.peershare.R
import com.example.prutc.peershare.data.entity.Item
import com.example.prutc.peershare.extension.inflate
import kotlinx.android.synthetic.main.view_item_group.view.*

class ItemViewGroup : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        inflate(R.layout.view_item_group)
    }

    private lateinit var itemList: List<Item>
    private lateinit var onClick: (Item, Int) -> Unit

    fun init(itemList: List<Item>, onClick: (Item, Int) -> Unit) {
        this.itemList = itemList
        this.onClick = onClick
        renderItemViewGroup()
    }

    private fun renderItemViewGroup() {
        llItem.removeAllViews()
        itemList.forEachIndexed { index, item ->
            llItem.addView(ItemView(context).apply {
                init(item, index, onClick)
            })
        }
    }
}