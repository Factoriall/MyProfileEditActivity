package com.example.myprofileeditactivity

import com.example.myprofileeditactivity.model.*
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * 클래스에 대한 간단한 설명이나 참고 url을 남겨주세요.
 * Created by fac.toriall on 2023.02.19..
 */
class ProfileEditViewModelFake : ProfileEditInfo {
    override var editItemList: MutableStateFlow<List<EditItem>> = MutableStateFlow(listOf())
    override var clickedItem: MutableStateFlow<EditItem?> = MutableStateFlow(null)
    override var maxItemId: MutableStateFlow<Long> = MutableStateFlow(0)

    override fun changeClickedBox(itemType: EditItemType, it: EditItem) {
        TODO("Not yet implemented")
    }

    override fun changeClickedBoxPoint(itemType: EditItemType, point: PointRatio) {
        TODO("Not yet implemented")
    }

    override fun removeClickedBox(it: EditItem) {
        TODO("Not yet implemented")
    }

    override fun changeClickedBoxSize(itemType: EditItemType, itemSize: ItemSizeRatio) {
        TODO("Not yet implemented")
    }

    override fun addEditItem(itemType: EditItemType) {
        TODO("Not yet implemented")
    }

    override fun applyClickedBoxSize() {
        TODO("Not yet implemented")
    }

    override fun applyClickedBoxPoint() {
        TODO("Not yet implemented")
    }
}
