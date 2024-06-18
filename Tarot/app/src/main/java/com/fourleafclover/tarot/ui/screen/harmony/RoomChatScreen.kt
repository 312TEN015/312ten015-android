package com.fourleafclover.tarot.ui.screen.harmony

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOutQuart
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.getRandomCards
import com.fourleafclover.tarot.pickedTopicNumber
import com.fourleafclover.tarot.ui.component.AppBarClose
import com.fourleafclover.tarot.ui.component.ButtonText
import com.fourleafclover.tarot.ui.component.getBackgroundModifier
import com.fourleafclover.tarot.ui.theme.TextB03M14
import com.fourleafclover.tarot.ui.theme.TextB04M12
import com.fourleafclover.tarot.ui.theme.backgroundColor_2
import com.fourleafclover.tarot.ui.theme.gray_1
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_7
import com.fourleafclover.tarot.ui.theme.gray_8
import com.fourleafclover.tarot.ui.theme.highlightPurple
import com.fourleafclover.tarot.ui.theme.purple50
import com.fourleafclover.tarot.utils.getCardImageId
import com.fourleafclover.tarot.utils.getPickedTopic
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


private val cards = mutableStateListOf<Int>().apply { addAll(getRandomCards()) }
private var nowSelected by mutableIntStateOf(-1)


@Composable
@Preview
fun RoomChatScreen(
    navController: NavHostController = rememberNavController(),
    chatViewModel: ChatViewModel = remember { ChatViewModel() }
) {

    Column(modifier = getBackgroundModifier(backgroundColor_2)) {
        AppBarClose(
            navController = navController,
            pickedTopicTemplate = getPickedTopic(pickedTopicNumber),
            backgroundColor = backgroundColor_2,
            isTitleVisible = false
        )

        val scope = rememberCoroutineScope()
        val scrollState = rememberLazyListState()

        LazyColumn(
            state = scrollState
        ) {

            items(chatViewModel.getChatListSize()) {

                val chatItem = chatViewModel.getChatItem(it)
                val sec = chatViewModel.getSec(chatItem)

                withChatAnimation(idx = sec) {

                    when (chatItem.type) {
                        ChatType.PartnerChat -> {
                            PartnerChattingBox(
                                text = chatItem.text,
                                idx = sec
                            )
                            scope.launch {
                                scrollState.animateScrollToItem(index = chatViewModel.getChatListSize()-1)
                            }
                        }

                        ChatType.Button -> {
                            var buttonVisibility by remember { mutableStateOf(true) }
                            ButtonSelect(
                                text = chatItem.text,
                                onClick = {
                                    buttonVisibility = false
                                    chatViewModel.addChatItem(
                                        Chat(
                                            type = ChatType.MyChatText,
                                            text = chatItem.text
                                        )
                                    )
                                    chatViewModel.moveToNextScenario()
                                },
                                buttonVisibility
                            )
                            scope.launch {
                                scrollState.animateScrollToItem(index = chatViewModel.getChatListSize()-1)
                            }
                        }

                        ChatType.MyChatText -> {
                            MyChattingBox(text = chatItem.text)
                            scope.launch {
                                scrollState.animateScrollToItem(index = chatViewModel.getChatListSize()-1)
                            }
                        }

                        ChatType.MyChatImage -> {
                            MyChattingBox(drawable = chatItem.drawable)
                            scope.launch {
                                scrollState.animateScrollToItem(index = chatViewModel.getChatListSize()-1)
                            }
                        }

                        ChatType.PickCard -> {
                            CardDeck(chatViewModel)
                            scope.launch {
                                scrollState.animateScrollToItem(index = chatViewModel.getChatListSize()-1)
                            }
                        }

                        else -> {}
                    }

                }


            }

        }

    }

}

@Composable
fun CardDeck(chatViewModel: ChatViewModel) {
    val localContext = LocalContext.current
    val pxToMove = with(LocalDensity.current) { -32.dp.toPx().roundToInt() }

    // 카드덱
    Column(
        modifier = Modifier.padding(top = 80.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.Center
    ) {

        LazyRow(
            modifier = Modifier.wrapContentWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy((-46).dp)
        ) {

            items(cards.size) { index ->

                val offset by animateIntOffsetAsState(
                    targetValue = if (nowSelected == index) {
                        IntOffset(0, pxToMove)
                    } else {
                        IntOffset.Zero
                    },
                    label = "offset"
                )


                Image(painter = painterResource(id = R.drawable.tarot_front),
                    contentDescription = "$index",
                    modifier = Modifier
                        .width(80.dp)
                        .offset {
                            offset
                        }
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            Log.d("", "nowSelected: $index")
                            nowSelected = index
                        })
            }

        }

        ButtonSelect(
            text = "선택 완료",
            onClick = {
                chatViewModel.saveCardNumber(cards[nowSelected])
                chatViewModel.addChatItem(
                    Chat(
                        type = ChatType.MyChatImage,
                        drawable = getCardImageId(localContext, cards[nowSelected].toString())
                    )
                )
                cards.remove(cards[nowSelected])
                chatViewModel.moveToNextScenario()
                nowSelected = -1
            }
        )

    }
}

@Composable
fun withChatAnimation(idx: Int = 0, content: @Composable () -> Unit = {}) {
    val initialIdx = remember { idx }

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(((initialIdx + 1) * 1200).toLong())
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        modifier = Modifier,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = 1500, easing = EaseOutQuart),
            initialOffsetY = { it * 2 }
        ) + fadeIn(animationSpec = tween(durationMillis = 1900)),
        exit = fadeOut(animationSpec = tween(durationMillis = 500))
    ) {
        content()
    }
}

@Composable
@Preview
fun PartnerChattingBox(text: String = "", idx: Int = 0) {

    val initialIdx by remember { mutableStateOf(idx) }

    Row(
        modifier = Modifier
            .padding(start = 20.dp, end = 48.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            modifier = Modifier
                .padding(end = 14.dp)
                .alpha(if (initialIdx == 0) 1f else 0f),
            painter = painterResource(id = R.drawable.icon_love),
            contentDescription = null
        )

        Row(
            modifier = Modifier
                .padding(top = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.chat_tail_left_gray),
                contentDescription = null
            )

            Box(
                Modifier
                    .background(
                        color = gray_7,
                        shape = RoundedCornerShape(0.dp, 10.dp, 10.dp, 10.dp)
                    )
                    .padding(8.dp)

            ) {

                TextB03M14(
                    text = text,
                    color = gray_1
                )
            }
        }

    }


}

@Composable
@Preview
fun MyChattingBox(text: String = "", drawable: Int = 0) {

    Row(
        modifier = Modifier
            .padding(start = 48.dp, end = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
        ) {

            Box(
                Modifier
                    .background(
                        color = purple50,
                        shape = RoundedCornerShape(10.dp, 0.dp, 10.dp, 10.dp)
                    )
                    .padding(8.dp)

            ) {

                if (drawable != 0) {
                    Image(
                        modifier = Modifier.width(60.dp),
                        painter = painterResource(id = drawable),
                        contentDescription = null
                    )
                } else {
                    TextB03M14(
                        text = text,
                        color = gray_8
                    )
                }


            }

            Image(
                painter = painterResource(id = R.drawable.chat_tail_right_purple),
                contentDescription = null
            )
        }

    }
}

@Preview
@Composable
fun GuidBox(text: String = "상대방의 답변을 기다리는 중입니다...✏️") {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 40.dp), contentAlignment = Alignment.Center
    ) {
        TextB04M12(
            modifier = Modifier
                .background(color = gray_8, shape = RoundedCornerShape(6.dp))
                .padding(vertical = 8.dp, horizontal = 16.dp),
            text = text,
            color = gray_5,
            textAlign = TextAlign.Center
        )

    }

}


@Composable
fun ButtonSelect(
    text: String = "시작하기",
    onClick: () -> Unit,
    isVisible: Boolean = true
) {

    if (isVisible) {
        Box(
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .background(
                        shape = RoundedCornerShape(10.dp),
                        color = highlightPurple
                    )
                    .clickable {
                        onClick()
                    }
                    .padding(horizontal = 50.dp)
                    .wrapContentHeight(),
                contentAlignment = Alignment.Center
            ) {
                ButtonText(isEnabled = true, text = text, paddingVertical = 8.dp)
            }

        }
    }

}
