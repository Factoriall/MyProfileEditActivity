package com.example.myprofileeditactivity.model

/**
 * 클래스에 대한 간단한 설명이나 참고 url을 남겨주세요.
 * Created by fac.toriall on 2023.02.17..
 */
enum class EditItemType {
    PROFILE, TEXT, STICKER, BACKGROUND, SHAPE
}

val itemList = listOf(
    EditItemType.PROFILE, EditItemType.TEXT,
    EditItemType.STICKER, EditItemType.BACKGROUND, EditItemType.SHAPE
)