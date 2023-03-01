package com.example.myprofileeditactivity.model

/**
 * 클래스에 대한 간단한 설명이나 참고 url을 남겨주세요.
 * Created by fac.toriall on 2023.02.17..
 */

sealed class EditItem(
    open val itemId: Long,
    open var point: PointRatio,
    open val resizeType: ResizeType,
    open var size: ItemSizeRatio
) {
    abstract fun copy(
        item: EditItem,
        pnt: PointRatio? = null,
        ratio: ItemSizeRatio? = null
    ): EditItem

    data class ProfileImage(
        override val itemId: Long,
        override var point: PointRatio,
        override val resizeType: ResizeType = ResizeType.NONE,
        override var size: ItemSizeRatio,
    ) : EditItem(itemId, point, resizeType, size) {
        override fun copy(item: EditItem, pnt: PointRatio?, ratio: ItemSizeRatio?): EditItem {
            return ProfileImage(item.itemId, pnt ?: point, item.resizeType, ratio ?: size)
        }
    }

    data class Sticker(
        override val itemId: Long,
        override var point: PointRatio,
        override val resizeType: ResizeType = ResizeType.RATIO_FIX,
        val stickerId: Int,
        override var size: ItemSizeRatio
    ) : EditItem(itemId, point, resizeType, size) {
        override fun copy(item: EditItem, pnt: PointRatio?, ratio: ItemSizeRatio?): EditItem {
            TODO("Not yet implemented")
        }
    }

    data class Background(
        override val itemId: Long,
        override var point: PointRatio,
        override val resizeType: ResizeType = ResizeType.RATIO_NOT_FIXED,
        override var size: ItemSizeRatio
    ) : EditItem(itemId, point, resizeType, size) {
        override fun copy(item: EditItem, pnt: PointRatio?, ratio: ItemSizeRatio?): EditItem {
            TODO("Not yet implemented")
        }
    }

    data class Shape(
        override val itemId: Long,
        override var point: PointRatio,
        override val resizeType: ResizeType = ResizeType.RATIO_NOT_FIXED,
        val shapeId: Int,
        override var size: ItemSizeRatio
    ) : EditItem(itemId, point, resizeType, size) {
        override fun copy(item: EditItem, pnt: PointRatio?, ratio: ItemSizeRatio?): EditItem {
            TODO("Not yet implemented")
        }
    }

    data class Text(
        override val itemId: Long,
        override var point: PointRatio,
        override val resizeType: ResizeType = ResizeType.RATIO_FIX,
        val text: String,
        override var size: ItemSizeRatio
    ) : EditItem(itemId, point, resizeType, size) {
        override fun copy(item: EditItem, pnt: PointRatio?, ratio: ItemSizeRatio?): EditItem {
            TODO("Not yet implemented")
        }
    }
}

enum class ResizeType {
    NONE, RATIO_FIX, RATIO_NOT_FIXED
}