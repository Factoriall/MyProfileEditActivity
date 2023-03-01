package com.example.myprofileeditactivity

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofileeditactivity.model.*


/**
 * 클래스에 대한 간단한 설명이나 참고 url을 남겨주세요.
 * Created by fac.toriall on 2023.01.24..
 */
class MainActivity : ComponentActivity() {
    private val viewModel: ProfileEditViewModelImpl by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileEditScreen(viewModel)
        }
    }
}

@Composable
fun ProfileEditScreen(viewModel: ProfileEditInfo) {
    // LockScreenLandscape()
    val profileEditItems = viewModel.editItemList.collectAsState().value
    val clickedItem = viewModel.clickedItem.collectAsState().value

    var screenWidth by remember { mutableStateOf(0.dp) }
    var screenHeight by remember { mutableStateOf(0.dp) }

    val current = LocalDensity.current
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = Color.White)
            .onGloballyPositioned {
                with(current) {
                    screenWidth = it.size.width.toDp()
                    screenHeight = it.size.height.toDp()
                }
            }
    ) {
        EditSelectBox {
            viewModel.addEditItem(it)
        }

        val canvasWidth = (screenWidth - 60.dp) * 0.8f + 40.dp
        val canvasHeight = canvasWidth * 0.5f + 40.dp

        Box(
            modifier = Modifier
                .padding(end = 60.dp)
                .width(canvasWidth)
                .height(canvasHeight)
                .background(color = Color.Yellow)
                .align(Alignment.CenterEnd)
        ) {
            Box(modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
                .background(color = Color.Blue)
                .clickable {
                    viewModel.clickedItem.value = null
                })
            for (item in profileEditItems) {
                when (item) {
                    is EditItem.ProfileImage -> {
                        ProfileImageBox(canvasWidth, canvasHeight, item, clickedItem?.itemId,
                            onPressBox = {
                                viewModel.changeClickedBox(EditItemType.PROFILE, it)
                            }, onDragBox = { point ->
                                viewModel.changeClickedBoxPoint(
                                    EditItemType.PROFILE,
                                    PointRatio(
                                        point.x / canvasWidth,
                                        point.y / canvasHeight
                                    )
                                )
                            }, onClickResizeButton = { itemSize ->
                                viewModel.changeClickedBoxSize(
                                    EditItemType.PROFILE,
                                    ItemSizeRatio(
                                        itemSize.width / canvasWidth,
                                        itemSize.height / itemSize.width
                                    )
                                )
                            }, onClickRemoveButton = {
                                viewModel.removeClickedBox(it)
                            },
                            onDragEnd = {
                                viewModel.applyClickedBoxPoint()
                            }
                        )
                    }
                    else -> {}
                }
            }

            if (clickedItem != null) {
                val xOffset = canvasWidth * clickedItem.point.xRatio
                val yOffset = canvasHeight * clickedItem.point.yRatio
                val width = canvasWidth * clickedItem.size.widthRatio
                val height =
                    canvasWidth * clickedItem.size.widthRatio * clickedItem.size.aspectRatio
                Box(
                    modifier = Modifier
                        .offset(xOffset - 20.dp, yOffset - 20.dp)
                        .width(width + 40.dp)
                        .height(height + 40.dp)
                        .border(
                            width = 1.dp,
                            shape = RectangleShape,
                            color = Color.Gray
                        )
                )

                Box(
                    modifier = Modifier
                        .offset(
                            xOffset + width + 12.dp,
                            yOffset + height + 12.dp
                        )
                        .size(16.dp)
                        .background(
                            color = Color.DarkGray,
                            shape = CircleShape
                        )
                ) {
                    Image(
                        painterResource(id = R.drawable.icon_resize),
                        contentDescription = null
                    )
                }

                Box(
                    modifier = Modifier
                        .offset(
                            xOffset - 28.dp,
                            yOffset + height + 12.dp
                        )
                        .size(16.dp)
                        .background(
                            color = Color.DarkGray,
                            shape = CircleShape
                        )
                ) {
                    Image(
                        painterResource(id = R.drawable.icon_rotate),
                        contentDescription = null
                    )
                }

                Box(
                    modifier = Modifier
                        .offset(
                            xOffset + width + 12.dp,
                            yOffset - 28.dp
                        )
                        .size(16.dp)
                        .background(
                            color = Color.DarkGray,
                            shape = CircleShape
                        )
                ) {
                    Image(painterResource(id = R.drawable.icon_close), contentDescription = null)
                }
            }
        }


        /*
        Box(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .background(color = Color(0xcc000000))
                .align(Alignment.BottomCenter)
        ) {
            EditItemListScreen()
        }*/

    }
}

@Composable
fun ProfileImageBox(
    parentWidth: Dp,
    parentHeight: Dp,
    item: EditItem.ProfileImage,
    clickedItemId: Long?,
    onPressBox: (EditItem) -> Unit,
    onDragBox: (Point) -> Unit,
    onDragEnd: () -> Unit,
    onClickResizeButton: (ItemSize) -> Unit,
    onClickRemoveButton: (EditItem) -> Unit,
) {
    val offsetX = remember { mutableStateOf(parentWidth * item.point.xRatio) }
    val offsetY = remember { mutableStateOf(parentHeight * item.point.yRatio) }

    val itemWidth = remember { mutableStateOf(parentWidth * item.size.widthRatio) }
    val itemHeight = remember { mutableStateOf(itemWidth.value * item.size.aspectRatio) }

    Box(
        modifier = Modifier
            .offset(offsetX.value - 20.dp, offsetY.value - 20.dp)
            .width(itemWidth.value + 40.dp)
            .height(itemHeight.value + 40.dp)
    ) {
        Box(
            modifier = Modifier
                .offset(20.dp, 20.dp)
                .width(itemWidth.value)
                .height(itemHeight.value)
                .background(
                    color = Color.Green,
                    shape = RoundedCornerShape(2.dp)
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { onPressBox(item) }
                    )
                }
                .pointerInput(Unit) {
                    detectDragGestures(onDrag = { change, dragAmount ->
                        change.consume()

                        offsetX.value = (offsetX.value + (dragAmount.x).toDp()).coerceIn(
                            20.dp,
                            parentWidth - itemWidth.value - 20.dp
                        )
                        offsetY.value = (offsetY.value + (dragAmount.y).toDp()).coerceIn(
                            20.dp,
                            parentHeight - itemHeight.value - 20.dp
                        )

                        onDragBox(Point(offsetX.value, offsetY.value))
                    }, onDragEnd = { onDragEnd() })
                }
        )

        if (item.itemId == clickedItemId) {
            ClickedBox(
                item,
                itemWidth,
                itemHeight,
                parentWidth,
                parentHeight,
                offsetX,
                offsetY,
                onClickResizeButton,
                onClickRemoveButton
            )
        }
    }
}

@Composable
fun ClickedBox(
    item: EditItem,
    itemWidth: MutableState<Dp>,
    itemHeight: MutableState<Dp>,
    parentWidth: Dp,
    parentHeight: Dp,
    offsetX: MutableState<Dp>,
    offsetY: MutableState<Dp>,
    onClickResizeButton: (ItemSize) -> Unit,
    onClickRemoveButton: (EditItem) -> Unit
) {

    Box(
        modifier = Modifier
            .offset(
                itemWidth.value + 32.dp,
                itemHeight.value + 32.dp
            )
            .size(16.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    itemWidth.value =
                        (itemWidth.value * (1 + ((dragAmount.x).toDp() / itemWidth.value))).coerceIn(
                            parentWidth * 0.1f,
                            (parentWidth - 20.dp - offsetX.value).coerceAtMost(parentWidth - 40.dp)
                        )
                    itemHeight.value =
                        (itemHeight.value * (1 + ((dragAmount.y).toDp() / itemHeight.value))).coerceIn(
                            parentWidth * 0.1f,
                            (parentHeight - 20.dp - offsetY.value).coerceAtMost(parentHeight - 40.dp)
                        )

                    onClickResizeButton(ItemSize(itemWidth.value, itemHeight.value))
                }
            }
    )

    Box(
        modifier = Modifier
            .offset(
                itemWidth.value + 32.dp,
                itemHeight.value - 8.dp
            )
            .size(16.dp)
            .clickable { onClickRemoveButton(item) }
    )
}

@Composable
fun EditSelectBox(onClickEditSelectBox: (EditItemType) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(60.dp), contentAlignment = Alignment.CenterEnd
    ) {

        LazyColumn {
            items(itemList) { string ->
                TextCard(string, onClickEditSelectBox)
            }
        }
    }
}

@Composable
fun EditItemListScreen() {  // TODO: 프로필 편집 관련 아이템 모아두는 칸

}

@Composable
fun TextCard(
    item: EditItemType,
    onClickEditSelectBox: (EditItemType) -> Unit
) {
    val title = when (item) {
        EditItemType.PROFILE -> "프로필"
        EditItemType.TEXT -> "텍스트"
        EditItemType.STICKER -> "스티커"
        EditItemType.BACKGROUND -> "배경 화면"
        EditItemType.SHAPE -> "도형"
    }

    Divider(Modifier.width(60.dp), thickness = 1.dp, color = Color.Black)
    Column(
        modifier = Modifier
            .height(40.dp)
            .width(60.dp)
            .background(Color.Gray)
            .clickable { onClickEditSelectBox(item) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = title, fontSize = 14.sp, textAlign = TextAlign.Center)
    }
}

@Composable
fun LockScreenLandscape() { //
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val activity = context as Activity
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        onDispose {
            activity.requestedOrientation = originalOrientation
        }
    }
}

@Composable
@Preview(device = Devices.AUTOMOTIVE_1024p, widthDp = 720, heightDp = 360)
fun TestScreen() {
    val viewModel = ProfileEditViewModelFake()
    ProfileEditScreen(viewModel)
}
