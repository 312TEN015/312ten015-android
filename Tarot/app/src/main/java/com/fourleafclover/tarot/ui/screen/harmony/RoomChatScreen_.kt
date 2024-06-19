package com.fourleafclover.tarot.ui.screen.harmony

import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOutQuart
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.Dp
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
import com.fourleafclover.tarot.ui.theme.transparent
import com.fourleafclover.tarot.ui.theme.white
import com.fourleafclover.tarot.utils.getCardImageId
import com.fourleafclover.tarot.utils.getPickedTopic
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@Composable
@Preview
fun RoomChatScreen_(
    navController: NavHostController = rememberNavController(),
    chatViewModel: ChatViewModel_ = remember { ChatViewModel_() }
) {

    Column(modifier = getBackgroundModifier(backgroundColor_2)) {
        AppBarClose(
            navController = navController,
            pickedTopicTemplate = getPickedTopic(pickedTopicNumber),
            backgroundColor = backgroundColor_2,
            isTitleVisible = false
        )

        Box(modifier = Modifier) {

            val scrollState = rememberLazyListState()
            val scope = rememberCoroutineScope()

            LazyColumn(
                modifier = Modifier,
                state = scrollState,
                contentPadding = PaddingValues(vertical = 0.dp),
                content = {

                    itemsIndexed(
                        items = chatViewModel.chatList
                    ) { idx, chatItem ->
                        val sec = remember { chatViewModel.getSec(chatItem) }
                        when (chatItem.type) {
                            ChatType.PartnerChat -> {
                                withChatAnimation_(idx = sec) {
                                    PartnerChattingBox(
                                        text = chatItem.text,
                                        idx = sec
                                    )
                                    scope.launch {
//                                        scrollState.animateScrollToItem(index = chatViewModel.chatList.lastIndex)
                                    }
                                }
                            }

                            ChatType.Button -> {
                                withChatAnimation_(idx = sec) {
                                    ButtonSelect(text = chatItem.text, onClick = {
                                        chatViewModel.chatList.removeLast()
                                        chatViewModel.insertedNum += 1
                                        chatViewModel.addChat(Chat(ChatType.MyChat, chatItem.text, code = "inserted_${chatViewModel.insertedNum}"))
                                        chatViewModel.moveToNextScenario()
                                    })
                                }
                            }

                            ChatType.GuidText -> {
                                withChatAnimation_(idx = sec) {
                                    GuidBox(text = chatItem.text)
                                    scope.launch {
//                                        scrollState.animateScrollToItem(index = chatViewModel.chatList.lastIndex)
                                    }
                                }
                            }

                            ChatType.MyChat -> {
                                withChatAnimation_(idx = sec) {
                                    MyChattingBox(text = chatItem.text, drawable = chatItem.drawable)
                                    scope.launch {
//                                        scrollState.animateScrollToItem(index = chatViewModel.chatList.lastIndex)
                                    }
                                }
                            }

                            ChatType.PickCard -> {
                                CardDeck_(sec, chatViewModel)
                                scope.launch {
//                                    scrollState.animateScrollToItem(index = chatViewModel.chatList.lastIndex)
                                }
                            }

                            else -> {}
                        }

                        if (idx == chatViewModel.chatList.lastIndex){
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .background(color = transparent))
                        }
                    }

                })


        }





    }

}

@Composable
fun CardDeck_(idx: Int, chatViewModel: ChatViewModel_) {
    val localContext = LocalContext.current
    val cards = remember { mutableStateListOf<Int>().apply { addAll(getRandomCards()) } }
    var nowSelected by remember { mutableIntStateOf(-1) }
    var cardSelected by remember { mutableStateOf(false) }
    val pxToMove = with(LocalDensity.current) { -32.dp.toPx().roundToInt() }

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
        exit = fadeOut(animationSpec = tween(durationMillis = 800))
    ) {

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
                                cardSelected = true
                                nowSelected = index
                            })
                }

            }

            ButtonSelect(
                text = "선택 완료",
                onClick = {
                    chatViewModel.chatList.removeLast()
                    chatViewModel.saveCardNumber(cards[nowSelected])
                    chatViewModel.insertedNum += 1
                    chatViewModel.addChat(
                        Chat(
                            ChatType.MyChat,
                            drawable = getCardImageId(localContext, cards[nowSelected].toString()),
                            code = "inserted_${chatViewModel.insertedNum}"
                        )
                    )
                    cards.remove(cards[nowSelected])
                    chatViewModel.moveToNextScenario()
                    chatViewModel.isCardPick = false
                    visible = false

                    cardSelected = false
                    nowSelected = -1
                },
                isVisible = cardSelected
            )

        }
    }
}

@Composable
fun withChatAnimation_(idx: Int = 0, content: @Composable () -> Unit = {}) {
    val initialIdx = remember { idx }

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(((initialIdx + 1) * 1200).toLong())
        visible = true
    }

    Box(modifier = Modifier)
    AnimatedVisibility(
        visible = visible,
        modifier = Modifier,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = 1500, easing = EaseOutQuart),
            initialOffsetY = { it * 2 }
        ) + fadeIn(animationSpec = tween(durationMillis = 1900))
    ) {
        content()
    }
}

@Composable
@Preview
fun PartnerChattingBox_(text: String = "", idx: Int = 0) {

    val initialIdx = remember { idx }

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
fun MyChattingBox_(text: String = "", drawable: Int = 0) {

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
fun GuidBox_(text: String = "상대방의 답변을 기다리는 중입니다...✏️") {

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
fun ButtonSelect_(
    text: String = "시작하기",
    onClick: () -> Unit,
    isVisible: Boolean = true
) {

    Box(
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxWidth()
            .alpha(if (isVisible) 1f else 0f),
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
