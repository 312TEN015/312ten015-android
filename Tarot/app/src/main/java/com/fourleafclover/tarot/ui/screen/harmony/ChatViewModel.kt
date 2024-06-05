package com.fourleafclover.tarot.ui.screen.harmony

import android.graphics.drawable.AdaptiveIconDrawable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.fourleafclover.tarot.harmonyViewModel

val scenarioSequence = arrayListOf(
    Scenario.Opening,
    Scenario.FirstCard,
    Scenario.SecondCard,
    Scenario.ThirdCard,
    Scenario.Complete
)

val opening = listOf(
    Chat(ChatType.PartnerChat, "${harmonyViewModel.getUserNickname()}님, 그 사람과의 운명이 궁금하시군요!"),
    Chat(ChatType.PartnerChat, "지금부터 타로카드를 통해 서로의 운명 궁합을 봐드릴게요!\n궁합 보실 준비가 되셨다면 [시작하기]를 눌러주세요!"),
    Chat(ChatType.Button, "시작하기")
)

val firstCard = listOf(
    Chat(ChatType.PartnerChat, "두분 모두 궁합 볼 준비가 되셨군요! 이제부터 차례대로 총 세장의 카드를 선택하실 수 있어요."),
    Chat(ChatType.PartnerChat, "서로 선택한 카드를 기반으로, 두분의 궁합 운명을 해석해드릴게요.\uD83D\uDD2E"),
    Chat(ChatType.PartnerChat, "우선 상대방을 떠올리며 첫번째 카드를 골라주세요."),
)

val secondCard = listOf(
    Chat(ChatType.PartnerChat, "첫번째 카드 선택이 완료되었습니다! {의미심장한} 카드를 뽑으셨네요✨"),
    Chat(ChatType.PartnerChat, "${harmonyViewModel.getPartnerNickname()}님은 이 카드를 선택하셨어요."),
    Chat(ChatType.PartnerChat, "이제 두번째 카드를 골라봐요!"),
)

val thirdCard = listOf(
    Chat(ChatType.PartnerChat, "두번째 카드 선택이 완료되었습니다! {의미심장한} 카드를 뽑으셨네요✨"),
    Chat(ChatType.PartnerChat, "${harmonyViewModel.getPartnerNickname()}님은 이 카드를 선택하셨어요."),
    Chat(ChatType.PartnerChat, "이제 세번째 카드를 골라봐요!"),
)

val complete = listOf(
    Chat(ChatType.PartnerChat, "짝짝짝\uD83D\uDC4F\n모든 카드 선택이 완료되었습니다! ${harmonyViewModel.getUserNickname()}님과 ${harmonyViewModel.getPartnerNickname()}님의 궁합은...")
)

data class Chat(val type: ChatType, val text: String = "", val drawable: Int = 0)

enum class ChatType {
    PartnerChat,
    MyChat,
    GuidText,
    Button
}

enum class Scenario {
    Opening,
    FirstCard,
    SecondCard,
    ThirdCard,
    Complete
}

class ChatViewModel : ViewModel() {

    var chatList = mutableStateListOf<Chat>()
    private var nowScenario = Scenario.Opening


    init {
        initChatList()
    }

    private fun initChatList() {
        chatList.addAll(opening)
    }


    private fun addNextScenario(nextScenario: Scenario) {
        when (nextScenario) {
            Scenario.FirstCard -> chatList.addAll(firstCard)
            Scenario.SecondCard -> chatList.addAll(secondCard)
            Scenario.ThirdCard -> chatList.addAll(thirdCard)
            Scenario.Complete -> chatList.addAll(complete)
            else -> {}
        }

    }

    fun getSec(chat: Chat): Int{
        return when (nowScenario) {
            Scenario.Opening -> opening.indexOf(chat)
            Scenario.FirstCard -> firstCard.indexOf(chat)
            Scenario.SecondCard -> secondCard.indexOf(chat)
            Scenario.ThirdCard -> thirdCard.indexOf(chat)
            Scenario.Complete -> complete.indexOf(chat)
            else -> { 0 }
        }
    }

    fun addChat(chat: Chat){
        chatList.add(chat)
    }

    fun moveToNextScenario(){
        if (scenarioSequence.indexOf(nowScenario) + 1 == scenarioSequence.size) return

        nowScenario = scenarioSequence[scenarioSequence.indexOf(nowScenario) + 1]
        harmonyViewModel.setRoomScenario(nowScenario)
        addNextScenario(nowScenario)

    }
}