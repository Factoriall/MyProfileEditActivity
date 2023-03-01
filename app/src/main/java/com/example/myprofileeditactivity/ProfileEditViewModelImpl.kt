package com.example.myprofileeditactivity

import androidx.lifecycle.ViewModel
import com.example.myprofileeditactivity.model.*
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * 클래스에 대한 간단한 설명이나 참고 url을 남겨주세요.
 * Created by fac.toriall on 2023.02.19..
 */
class ProfileEditViewModelImpl : ViewModel(), ProfileEditInfo {
    override var editItemList: MutableStateFlow<List<EditItem>> = MutableStateFlow(listOf())
    override var clickedItem: MutableStateFlow<EditItem?> = MutableStateFlow(null)
    override var maxItemId: MutableStateFlow<Long> = MutableStateFlow(0)

    override fun changeClickedBox(itemType: EditItemType, item: EditItem) {
        clickedItem.value = when (itemType) {
            EditItemType.PROFILE -> {
                EditItem.ProfileImage(
                    itemId = item.itemId,
                    point = item.point,
                    size = item.size,
                    resizeType = item.resizeType
                )
            }
            else -> {
                EditItem.ProfileImage(
                    itemId = item.itemId,
                    point = item.point,
                    size = item.size,
                    resizeType = item.resizeType
                )
            }
        }
    }

    override fun changeClickedBoxPoint(itemType: EditItemType, point: PointRatio) {
        val item = clickedItem.value ?: return
        clickedItem.value = when (itemType) {
            EditItemType.PROFILE -> {
                item.copy(item, pnt = point)
            }
            else -> {
                item.copy(item, pnt = point)
            }
        }
    }

    override fun removeClickedBox(it: EditItem) {
        editItemList.value = editItemList.value.filter { it.itemId != clickedItem.value?.itemId }
        clickedItem.value = null
    }

    override fun changeClickedBoxSize(itemType: EditItemType, itemSize: ItemSizeRatio) {
        val item = clickedItem.value ?: return
        clickedItem.value = when (itemType) {
            EditItemType.PROFILE -> {
                EditItem.ProfileImage(
                    itemId = item.itemId,
                    point = item.point,
                    size = itemSize,
                    resizeType = item.resizeType
                )
            }
            else -> {
                EditItem.ProfileImage(
                    itemId = item.itemId,
                    point = item.point,
                    size = itemSize,
                    resizeType = item.resizeType
                )
            }
        }
        applyClickedBoxSize()
    }

    override fun applyClickedBoxSize() {
        val item = clickedItem.value ?: return
        editItemList.value.find { it.itemId == clickedItem.value?.itemId }?.size = item.size
    }

    override fun applyClickedBoxPoint() {
        val item = clickedItem.value ?: return
        editItemList.value.find { it.itemId == clickedItem.value?.itemId }?.point = item.point
    }

    override fun addEditItem(itemType: EditItemType) {
        val nextId = maxItemId.value
        val editItem: EditItem = when (itemType) {
            EditItemType.PROFILE -> EditItem.ProfileImage(
                itemId = nextId,
                point = PointRatio(0.5f, 0.5f),
                size = ItemSizeRatio(0.1f, 1f)
            )
            EditItemType.TEXT -> EditItem.ProfileImage(
                itemId = nextId,
                point = PointRatio(0.5f, 0.5f),
                size = ItemSizeRatio(0.1f, 1f)
            )
            EditItemType.STICKER -> EditItem.ProfileImage(
                itemId = nextId,
                point = PointRatio(0.5f, 0.5f),
                size = ItemSizeRatio(0.1f, 1f)
            )
            EditItemType.BACKGROUND -> EditItem.ProfileImage(
                itemId = nextId,
                point = PointRatio(0.5f, 0.5f),
                size = ItemSizeRatio(0.1f, 1f)
            )
            EditItemType.SHAPE -> EditItem.ProfileImage(
                itemId = nextId,
                point = PointRatio(0.5f, 0.5f),
                size = ItemSizeRatio(0.1f, 1f)
            )
        }
        editItemList.value = editItemList.value.plus(editItem)
        clickedItem.value = editItemList.value.last()
        maxItemId.value += 1
    }

}