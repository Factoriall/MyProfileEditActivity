package com.example.myprofileeditactivity

import com.example.myprofileeditactivity.model.*
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * 클래스에 대한 간단한 설명이나 참고 url을 남겨주세요.
 * Created by fac.toriall on 2023.02.19..
 */
interface ProfileEditInfo {
    var editItemList: MutableStateFlow<List<EditItem>>
    var clickedItem: MutableStateFlow<EditItem?>
    var maxItemId: MutableStateFlow<Long>

    fun changeClickedBox(itemType: EditItemType, item: EditItem)
    fun changeClickedBoxPoint(itemType: EditItemType, point: PointRatio)
    fun removeClickedBox(it: EditItem)
    fun changeClickedBoxSize(itemType: EditItemType, itemSize: ItemSizeRatio)
    fun addEditItem(itemType: EditItemType)
    fun applyClickedBoxSize()
    fun applyClickedBoxPoint()
}