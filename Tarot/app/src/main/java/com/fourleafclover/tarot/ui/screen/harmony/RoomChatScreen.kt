package com.fourleafclover.tarot.ui.screen.harmony

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOutQuart
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.R
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
import com.fourleafclover.tarot.utils.getPickedTopic
import kotlinx.coroutines.delay

enum class ActionStatus {
    Unselected,
    Selected
}

val chatViewModel = ChatViewModel()


@Composable
@Preview
fun RoomChatScreen(
    navController: NavHostController = rememberNavController()
) {

    Column(modifier = getBackgroundModifier(backgroundColor_2)) {
        AppBarClose(
            navController = navController,
            pickedTopicTemplate = getPickedTopic(pickedTopicNumber),
            backgroundColor = backgroundColor_2,
            isTitleVisible = false
        )

        Box(modifier = Modifier.fillMaxSize()) {

            LazyColumn(
                contentPadding = PaddingValues(vertical = 0.dp),
                content = {

                    items(chatViewModel.chatList) { chatItem ->
                        when (chatItem.type) {
                            ChatType.PartnerChat -> {
                                withChatAnimation(idx = chatViewModel.getSec(chatItem)) {
                                    PartnerChattingBox(text = chatItem.text, idx = chatViewModel.getSec(chatItem))
                                }
                            }
                            ChatType.Button -> {
                                withChatAnimation(idx = chatViewModel.getSec(chatItem)) {
                                    ButtonSelect(text = chatItem.text,)
                                }
                            }
                            ChatType.GuidText -> {
                                withChatAnimation(idx = chatViewModel.getSec(chatItem)) {
                                    GuidBox(text = chatItem.text,)
                                }
                            }
                            ChatType.MyChat -> {
                                withChatAnimation(idx = chatViewModel.getSec(chatItem)) {
                                    MyChattingBox(text = chatItem.text,)
                                }
                            }

                            else -> {}
                        }
                    }

                })


        }
    }

}

@Composable
fun withChatAnimation(idx: Int = 0, content:  @Composable () -> Unit = {}){
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
fun PartnerChattingBox(text: String = "", idx: Int = 0) {

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
fun MyChattingBox(text: String = "시작하기") {

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

                TextB03M14(
                    text = text,
                    color = gray_8
                )
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
    text: String = "시작하기"
) {

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
                    chatViewModel.chatList.removeLast()
                    chatViewModel.addChat(Chat(ChatType.MyChat, text))
                    chatViewModel.moveToNextScenario()
                }
                .padding(horizontal = 50.dp)
                .wrapContentHeight(),
            contentAlignment = Alignment.Center
        ) {
            ButtonText(isEnabled = true, text = text, paddingVertical = 8.dp)
        }

    }

}
