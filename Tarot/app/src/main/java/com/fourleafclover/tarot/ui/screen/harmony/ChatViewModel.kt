package com.fourleafclover.tarot.ui.screen.harmony

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.fourleafclover.tarot.harmonyViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

val scenarioSequence = arrayListOf(
    Scenario.Opening,
    Scenario.FirstCard,
    Scenario.SecondCard,
    Scenario.ThirdCard,
    Scenario.Complete
)

val opening = listOf(
    Chat(
        ChatType.PartnerChatText,
        "${harmonyViewModel.getUserNickname()}님, 그 사람과의 운명이 궁금하시군요!",
        code = "opening_1"
    ),
    Chat(
        ChatType.PartnerChatText,
        "지금부터 타로카드를 통해 서로의 운명 궁합을 봐드릴게요!\n궁합 보실 준비가 되셨다면 [시작하기]를 눌러주세요!",
        code = "opening_2"
    ),
//    Chat(ChatType.PartnerChatText, "지금부터 타로카드를 통해 서로의 운명 궁합을 봐드릴게요!\n궁합 보실 준비가 되셨다면 [시작하기]를 눌러주세요!", code = "opening_22"),
//    Chat(ChatType.PartnerChatText, "지금부터 타로카드를 통해 서로의 운명 궁합을 봐드릴게요!\n궁합 보실 준비가 되셨다면 [시작하기]를 눌러주세요!", code = "opening_23"),
//    Chat(ChatType.PartnerChatText, "지금부터 타로카드를 통해 서로의 운명 궁합을 봐드릴게요!\n궁합 보실 준비가 되셨다면 [시작하기]를 눌러주세요!", code = "opening_24"),
//    Chat(ChatType.PartnerChatText, "지금부터 타로카드를 통해 서로의 운명 궁합을 봐드릴게요!\n궁합 보실 준비가 되셨다면 [시작하기]를 눌러주세요!", code = "opening_25"),
//    Chat(ChatType.PartnerChatText, "지금부터 타로카드를 통해 서로의 운명 궁합을 봐드릴게요!\n궁합 보실 준비가 되셨다면 [시작하기]를 눌러주세요!", code = "opening_26"),
//    Chat(ChatType.PartnerChatText, "지금부터 타로카드를 통해 서로의 운명 궁합을 봐드릴게요!\n궁합 보실 준비가 되셨다면 [시작하기]를 눌러주세요!", code = "opening_27"),
//    Chat(ChatType.PartnerChatText, "지금부터 타로카드를 통해 서로의 운명 궁합을 봐드릴게요!\n궁합 보실 준비가 되셨다면 [시작하기]를 눌러주세요!", code = "opening_28"),
//    Chat(ChatType.PartnerChatText, "지금부터 타로카드를 통해 서로의 운명 궁합을 봐드릴게요!\n궁합 보실 준비가 되셨다면 [시작하기]를 눌러주세요!", code = "opening_29"),
    Chat(ChatType.Button, "시작하기", code = "opening_3")
)

val firstCard = listOf(
    Chat(
        ChatType.PartnerChatText,
        "두분 모두 궁합 볼 준비가 되셨군요! 이제부터 차례대로 총 세장의 카드를 선택하실 수 있어요.",
        code = "firstCard_1"
    ),
    Chat(
        ChatType.PartnerChatText,
        "서로 선택한 카드를 기반으로, 두분의 궁합 운명을 해석해드릴게요.\uD83D\uDD2E",
        code = "firstCard_2"
    ),
    Chat(ChatType.PartnerChatText, "우선 상대방을 떠올리며 첫번째 카드를 골라주세요.", code = "firstCard_3"),
    Chat(ChatType.PickCard, code = "fristCard_4"),
)

val secondCard = listOf(
    Chat(ChatType.PartnerChatText, "첫번째 카드 선택이 완료되었습니다! {의미심장한} 카드를 뽑으셨네요✨", code = "secondCard_1"),
    Chat(
        ChatType.PartnerChatText,
        "${harmonyViewModel.getPartnerNickname()}님은 이 카드를 선택하셨어요.",
        code = "secondCard_2"
    ),
    Chat(ChatType.PartnerChatText, "이제 두번째 카드를 골라봐요!", code = "secondCard_3"),
    Chat(ChatType.PickCard, code = "secondCard_4"),
)

val thirdCard = listOf(
    Chat(ChatType.PartnerChatText, "두번째 카드 선택이 완료되었습니다! {의미심장한} 카드를 뽑으셨네요✨", code = "thirdCard_1"),
    Chat(
        ChatType.PartnerChatText,
        "${harmonyViewModel.getPartnerNickname()}님은 이 카드를 선택하셨어요.",
        code = "thirdCard_2"
    ),
    Chat(ChatType.PartnerChatText, "이제 세번째 카드를 골라봐요!", code = "thirdCard_3"),
    Chat(ChatType.PickCard, code = "thirdCard_4"),
)

val complete = listOf(
    Chat(
        ChatType.PartnerChatText,
        "짝짝짝\uD83D\uDC4F\n모든 카드 선택이 완료되었습니다! ${harmonyViewModel.getUserNickname()}님과 ${harmonyViewModel.getPartnerNickname()}님의 궁합은...",
        code = "complete_1"
    )
)

data class Chat(
    val type: ChatType,
    val text: String = "",
    val drawable: Int = 0,
    val code: String = "",
    var isShown: Boolean = false    // 애니메이션 재생 여부 판단을 위해
)

enum class ChatType {
    PartnerChatText,
    PartnerChatImage,
    PartnerChatButton,
    MyChatText,
    MyChatImage,
    GuidText,
    Button,
    PickCard,
}

enum class Scenario {
    Opening,
    FirstCard,
    SecondCard,
    ThirdCard,
    Complete
}

enum class CardPickStatus {
    Gathered,
    Spread
}

data class ChatState(
    var scenario: Scenario = Scenario.Opening,
    var cardPickStatus: CardPickStatus = CardPickStatus.Gathered,
    var firstCardNumber: Int = -1,
    var secondCardNumber: Int = -1,
    var thirdCardNumber: Int = -1
)

class ChatViewModel : ViewModel() {
    private val _chatState = MutableStateFlow(ChatState())
    val chatState: StateFlow<ChatState> = _chatState.asStateFlow()

    private val _partnerChatState = MutableStateFlow(ChatState())
    val partnerChatState: StateFlow<ChatState> = _partnerChatState.asStateFlow()

    private val chatList = mutableStateListOf<Chat>()

    init {
        chatList.addAll(opening)
        _chatState.value = ChatState()
        _partnerChatState.value = ChatState()
    }

    fun removeChatListLastItem() = chatList.removeLast()

    fun getChatListSize(): Int = chatList.size

    fun getChatItem(idx: Int): Chat = chatList[idx]

    fun addChatItem(chatItem: Chat) = chatList.add(chatItem)
    
    fun updateScenario(){
        val nowScenario = _chatState.value.scenario
        val nextScenario = scenarioSequence[scenarioSequence.indexOf(nowScenario) + 1]
        _chatState.value.scenario = nextScenario
    }

    fun moveToNextScenario() {
        updateScenario()    // 다음 시나리오로 업데이트
        addNextScenario()   // 업데이트 된 시나리오 진행
    }

    fun addNextScenario() {
        when (_chatState.value.scenario) {
            Scenario.FirstCard -> chatList.addAll(firstCard)
            Scenario.SecondCard -> chatList.addAll(secondCard)
            Scenario.ThirdCard -> chatList.addAll(thirdCard)
            Scenario.Complete -> chatList.addAll(complete)
            else -> {}
        }
    }

    fun getSec(chat: Chat): Int {
        return when (_chatState.value.scenario) {
            Scenario.Opening -> opening.indexOf(chat)
            Scenario.FirstCard -> firstCard.indexOf(chat)
            Scenario.SecondCard -> secondCard.indexOf(chat)
            Scenario.ThirdCard -> thirdCard.indexOf(chat)
            Scenario.Complete -> complete.indexOf(chat)
        }
    }

    fun saveCardNumber(cardNumber: Int) {
        when (_chatState.value.scenario) {
            Scenario.FirstCard -> _chatState.value.firstCardNumber = cardNumber
            Scenario.SecondCard -> _chatState.value.secondCardNumber = cardNumber
            Scenario.ThirdCard -> _chatState.value.thirdCardNumber = cardNumber
            else -> {
                0
            }
        }
    }

    fun updateCardPickStatus(newStatus: CardPickStatus) {
        _chatState.value = ChatState(
            _chatState.value.scenario,
            newStatus,
            _chatState.value.firstCardNumber,
            _chatState.value.secondCardNumber,
            _chatState.value.thirdCardNumber
        )
    }
}